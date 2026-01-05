package com.github.allisson95.algashop.billing.domain.model.invoice;

import java.time.Instant;
import java.util.UUID;

public record InvoiceIssuedEvent(
        UUID invoiceId,
        UUID customerId,
        String orderId,
        Instant issuedOn
) {

}
