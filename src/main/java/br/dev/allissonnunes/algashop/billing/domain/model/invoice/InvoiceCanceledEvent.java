package br.dev.allissonnunes.algashop.billing.domain.model.invoice;

import java.time.Instant;
import java.util.UUID;

public record InvoiceCanceledEvent(
        UUID invoiceId,
        UUID customerId,
        String orderId,
        Instant canceledOn
) {

}
