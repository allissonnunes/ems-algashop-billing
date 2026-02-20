package br.dev.allissonnunes.algashop.billing.application.invoice.query;

import br.dev.allissonnunes.algashop.billing.application.invoice.management.PayerData;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.InvoiceStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record InvoiceOutput(
        UUID id,
        String orderId,
        UUID customerId,
        Instant issuedAt,
        Instant paidAt,
        Instant canceledAt,
        Instant expiresAt,
        BigDecimal totalAmount,
        InvoiceStatus status,
        PayerData payer,
        PaymentSettingsOutput paymentSettings
) {

}
