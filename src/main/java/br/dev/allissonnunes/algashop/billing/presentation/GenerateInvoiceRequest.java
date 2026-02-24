package br.dev.allissonnunes.algashop.billing.presentation;

import br.dev.allissonnunes.algashop.billing.application.invoice.management.LineItemInput;
import br.dev.allissonnunes.algashop.billing.application.invoice.management.PayerData;
import br.dev.allissonnunes.algashop.billing.application.invoice.management.PaymentSettingsInput;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record GenerateInvoiceRequest(
        @NotNull
        UUID customerId,
        @NotNull
        @Valid
        PaymentSettingsInput paymentSettings,
        @NotNull
        @Valid
        PayerData payer,
        @NotEmpty
        List<@Valid LineItemInput> items
) {

}
