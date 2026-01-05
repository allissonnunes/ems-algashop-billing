package com.github.allisson95.algashop.billing.application.invoice.management;

import com.github.allisson95.algashop.billing.domain.model.creditcard.CreditCardRepository;
import com.github.allisson95.algashop.billing.domain.model.creditcard.CreditCardTestDataBuilder;
import com.github.allisson95.algashop.billing.domain.model.invoice.*;
import com.github.allisson95.algashop.billing.infrastructure.listener.InvoiceEventListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
class InvoiceManagementApplicationServiceIT {

    @MockitoSpyBean
    private InvoiceEventListener invoiceEventListener;

    @MockitoSpyBean
    private InvoicingService invoicingService;

    @Autowired
    private InvoiceRepository repository;

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

        final var invoice = repository.findById(invoiceId).orElseThrow();

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

        final var invoice = repository.findById(invoiceId).orElseThrow();

        assertThat(invoice.getStatus()).isEqualTo(InvoiceStatus.UNPAID);
        assertThat(invoice.getOrderId()).isEqualTo(input.orderId());

        verify(invoicingService).issue(any(), any(), any(), any());
    }

}