package com.github.allisson95.algashop.billing.application.invoice.management;

import com.github.allisson95.algashop.billing.domain.model.invoice.PaymentMethod;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public final class GenerateInvoiceInputTestDataBuilder {

    private GenerateInvoiceInputTestDataBuilder() {
        throw new IllegalStateException("Utility class");
    }

    public static GenerateInvoiceInput.GenerateInvoiceInputBuilder anInput() {
        return GenerateInvoiceInput.builder()
                .orderId("123ABC")
                .customerId(UUID.randomUUID())
                .paymentSettings(PaymentSettingsInput.builder()
                        .method(PaymentMethod.CREDIT_CARD)
                        .creditCardId(UUID.randomUUID())
                        .build())
                .payer(PayerData.builder()
                        .fullName("John Doe")
                        .email("john.doe@email.com")
                        .document("111.222.333-44")
                        .phone("11-99999-8888")
                        .address(AddressData.builder()
                                .street("Rua Teste")
                                .number("123")
                                .complement("Apto 101")
                                .neighborhood("Bairro Teste")
                                .city("Cidade Teste")
                                .state("SP")
                                .zipCode("12345-678")
                                .build())
                        .build())
                .items(Set.of(
                        LineItemInput.builder()
                                .name("Product 1")
                                .amount(new BigDecimal("200.00"))
                                .build()
                ))
                ;
    }

}
