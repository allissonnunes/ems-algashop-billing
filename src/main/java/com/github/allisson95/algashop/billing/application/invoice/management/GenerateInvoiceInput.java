package com.github.allisson95.algashop.billing.application.invoice.management;

import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record GenerateInvoiceInput(
        String orderId,
        UUID customerId,
        PaymentSettingsInput paymentSettings,
        PayerData payer,
        Set<LineItemInput> items
) {

}
