package br.dev.allissonnunes.algashop.billing.application.invoice.management;

import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.CreditCardNotFoundException;
import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.CreditCardRepository;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.*;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.Payment;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.PaymentGatewayService;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.PaymentRequest;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceManagementApplicationService {

    private final PaymentGatewayService paymentGatewayService;

    private final InvoicingService invoicingService;

    private final InvoiceRepository invoiceRepository;

    private final CreditCardRepository creditCardRepository;

    @Transactional
    public UUID generate(final GenerateInvoiceInput input) {
        final PaymentSettingsInput paymentSettings = input.paymentSettings();
        if (PaymentMethod.CREDIT_CARD == paymentSettings.method()) {
            this.verifyCreditCard(paymentSettings.creditCardId(), input.customerId());
        }

        final Payer payer = this.convertToPayer(input.payer());
        final Set<LineItem> items = this.convertToLineItems(input.items());

        final Invoice invoice = this.invoicingService.issue(input.orderId(), input.customerId(), payer, items);
        invoice.changePaymentSettings(paymentSettings.method(), paymentSettings.creditCardId());

        this.invoiceRepository.saveAndFlush(invoice);

        return invoice.getId();
    }

    @Transactional
    public void processPayment(final UUID invoiceId) {
        final Invoice invoice = this.invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException(invoiceId));

        final PaymentRequest paymentRequest = this.toPaymentRequest(invoice);

        final Payment payment;
        try {
            payment = this.paymentGatewayService.capture(paymentRequest);
        } catch (final Exception e) {
            final String errorMessage = "Payment capture failed";
            log.error(errorMessage, e);
            invoice.cancel(errorMessage);
            return;
        }

        invoicingService.assignPayment(invoice, payment);

        invoiceRepository.saveAndFlush(invoice);
    }

    @Transactional
    public void updateInvoiceStatus(final UUID invoiceId, final PaymentStatus paymentStatus) {
        final Invoice invoice = this.invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException(invoiceId));
        invoice.updatePaymentStatus(paymentStatus);
        invoiceRepository.saveAndFlush(invoice);
    }

    private void verifyCreditCard(final UUID creditCardId, final UUID customerId) {
        if (!this.creditCardRepository.existsByIdAndCustomerId(creditCardId, customerId)) {
            throw new CreditCardNotFoundException(creditCardId);
        }
    }

    private Payer convertToPayer(final PayerData payer) {
        final AddressData payerAddress = payer.address();
        return Payer.builder()
                .fullName(payer.fullName())
                .email(payer.email())
                .document(payer.document())
                .phone(payer.phone())
                .address(Address.builder()
                        .street(payerAddress.street())
                        .number(payerAddress.number())
                        .complement(payerAddress.complement())
                        .neighborhood(payerAddress.neighborhood())
                        .city(payerAddress.city())
                        .state(payerAddress.state())
                        .zipCode(payerAddress.zipCode())
                        .build())
                .build();
    }

    private Set<LineItem> convertToLineItems(final Collection<LineItemInput> itemsInput) {
        final Set<LineItem> items = new LinkedHashSet<>();

        int index = 1;
        for (final LineItemInput lineItemInput : itemsInput) {
            final LineItem lineItem = LineItem.builder()
                    .number(index++)
                    .name(lineItemInput.name())
                    .amount(lineItemInput.amount())
                    .build();
            items.add(lineItem);
        }

        return items;
    }

    private PaymentRequest toPaymentRequest(final Invoice invoice) {
        return PaymentRequest.builder()
                .method(invoice.getPaymentSettings().getMethod())
                .amount(invoice.getTotalAmount())
                .invoiceId(invoice.getId())
                .creditCardId(invoice.getPaymentSettings().getCreditCardId())
                .payer(invoice.getPayer())
                .build();
    }

}
