package br.dev.allissonnunes.algashop.billing.application.invoice.management;

import br.dev.allissonnunes.algashop.billing.application.AbstractApplicationIT;
import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.CreditCardRepository;
import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.CreditCardTestDataBuilder;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.*;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.Payment;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.PaymentGatewayService;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.PaymentRequest;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.PaymentStatus;
import br.dev.allissonnunes.algashop.billing.infrastructure.listener.InvoiceEventListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InvoiceManagementApplicationServiceIT extends AbstractApplicationIT {

    @MockitoSpyBean
    private InvoiceEventListener invoiceEventListener;

    @MockitoSpyBean
    private InvoicingService invoicingService;

    @MockitoBean
    private PaymentGatewayService paymentGatewayService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private InvoiceManagementApplicationService service;

    @Test
    void shouldGenerateInvoiceWithCreditCardAsPaymentMethod() {
        final var customerId = UUID.randomUUID();
        final var creditCard = CreditCardTestDataBuilder.aCreditCard().customerId(customerId).build();
        creditCardRepository.saveAndFlush(creditCard);

        final var input = GenerateInvoiceInputTestDataBuilder.anInput()
                .customerId(customerId)
                .paymentSettings(PaymentSettingsInput.builder()
                        .method(PaymentMethod.CREDIT_CARD)
                        .creditCardId(creditCard.getId())
                        .build())
                .build();
        final var invoiceId = service.generate(input);

        final var invoice = invoiceRepository.findById(invoiceId).orElseThrow();

        assertThat(invoice.getStatus()).isEqualTo(InvoiceStatus.UNPAID);
        assertThat(invoice.getOrderId()).isEqualTo(input.orderId());

        verify(invoicingService).issue(any(), any(), any(), any());

        verify(invoiceEventListener).listen(any(InvoiceIssuedEvent.class));
    }

    @Test
    void shouldGenerateInvoiceWithGatewayBalanceAsPaymentMethod() {
        final var input = GenerateInvoiceInputTestDataBuilder.anInput()
                .paymentSettings(PaymentSettingsInput.builder()
                        .method(PaymentMethod.GATEWAY_BALANCE)
                        .build())
                .build();
        final var invoiceId = service.generate(input);

        final var invoice = invoiceRepository.findById(invoiceId).orElseThrow();

        assertThat(invoice.getStatus()).isEqualTo(InvoiceStatus.UNPAID);
        assertThat(invoice.getOrderId()).isEqualTo(input.orderId());

        verify(invoicingService).issue(any(), any(), any(), any());
    }

    @Test
    public void shouldProcessInvoicePaymentAndCancelInvoice() {
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().build();
        invoice.changePaymentSettings(PaymentMethod.GATEWAY_BALANCE, null);
        invoiceRepository.saveAndFlush(invoice);

        Payment payment = Payment.builder()
                .gatewayCode("12345")
                .invoiceId(invoice.getId())
                .method(invoice.getPaymentSettings().getMethod())
                .status(PaymentStatus.FAILED)
                .build();

        when(paymentGatewayService.capture(any(PaymentRequest.class)))
                .thenReturn(payment);

        service.processPayment(invoice.getId());

        Invoice paidInvoice = invoiceRepository.findById(invoice.getId()).orElseThrow();

        assertThat(paidInvoice.isCanceled()).isTrue();

        verify(paymentGatewayService).capture(any(PaymentRequest.class));
        verify(invoicingService).assignPayment(any(Invoice.class), any(Payment.class));

        verify(invoiceEventListener).listen(any(InvoiceCanceledEvent.class));
    }

}