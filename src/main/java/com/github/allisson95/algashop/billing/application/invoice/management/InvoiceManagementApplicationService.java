package com.github.allisson95.algashop.billing.application.invoice.management;

import com.github.allisson95.algashop.billing.domain.model.creditcard.CreditCardNotFoundException;
import com.github.allisson95.algashop.billing.domain.model.creditcard.CreditCardRepository;
import com.github.allisson95.algashop.billing.domain.model.invoice.*;
import com.github.allisson95.algashop.billing.domain.model.invoice.payment.PaymentGatewayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import static java.util.Objects.nonNull;

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
        this.verifyCreditCardId(input.paymentSettings().creditCardId(), input.customerId());

        final Payer payer = this.convertToPayer(input.payer());
        final Set<LineItem> items = this.convertToLineItems(input.items());

        final Invoice invoice = this.invoicingService.issue(input.orderId(), input.customerId(), payer, items);
        invoice.changePaymentSettings(input.paymentSettings().method(), input.paymentSettings().creditCardId());

        this.invoiceRepository.saveAndFlush(invoice);

        return invoice.getId();
    }

    private void verifyCreditCardId(final UUID creditCardId, final UUID customerId) {
        if (nonNull(creditCardId) && !this.creditCardRepository.existsByIdAndCustomerId(creditCardId, customerId)) {
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

    private Set<LineItem> convertToLineItems(final Set<LineItemInput> itemsInput) {
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

}
