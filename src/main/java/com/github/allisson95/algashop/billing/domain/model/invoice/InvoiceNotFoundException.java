package com.github.allisson95.algashop.billing.domain.model.invoice;

import com.github.allisson95.algashop.billing.domain.model.DomainException;

import java.util.UUID;

public class InvoiceNotFoundException extends DomainException {

    public InvoiceNotFoundException(final UUID invoiceId) {
        super("Invoice with ID %s not found".formatted(invoiceId));
    }

}
