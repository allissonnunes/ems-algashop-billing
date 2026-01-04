package com.github.allisson95.algashop.billing.application.invoice.management;

import com.github.allisson95.algashop.billing.domain.model.creditcard.CreditCardRepository;
import com.github.allisson95.algashop.billing.domain.model.creditcard.CreditCardTestDataBuilder;
import com.github.allisson95.algashop.billing.domain.model.invoice.InvoiceRepository;
import com.github.allisson95.algashop.billing.domain.model.invoice.InvoiceStatus;
import com.github.allisson95.algashop.billing.domain.model.invoice.InvoicingService;
import com.github.allisson95.algashop.billing.domain.model.invoice.PaymentMethod;
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

    @Autowired
    private InvoiceManagementApplicationService service;

    @Autowired
    private InvoiceRepository repository;

    @MockitoSpyBean
    private InvoicingService invoicingService;

    @Autowired
    private CreditCardRepository creditCardRepository;

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