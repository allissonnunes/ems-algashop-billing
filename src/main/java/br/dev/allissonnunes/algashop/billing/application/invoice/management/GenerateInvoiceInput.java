package br.dev.allissonnunes.algashop.billing.application.invoice.management;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record GenerateInvoiceInput(
        String orderId,
        UUID customerId,
        PaymentSettingsInput paymentSettings,
        PayerData payer,
        List<LineItemInput> items
) {

}
