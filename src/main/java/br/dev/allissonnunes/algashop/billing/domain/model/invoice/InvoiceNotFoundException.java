package br.dev.allissonnunes.algashop.billing.domain.model.invoice;

import br.dev.allissonnunes.algashop.billing.domain.model.DomainException;

import java.util.UUID;

public class InvoiceNotFoundException extends DomainException {

    public InvoiceNotFoundException(final UUID invoiceId) {
        super("Invoice with ID %s not found".formatted(invoiceId));
    }

    public InvoiceNotFoundException(final String orderId) {
        super("Invoice with Order ID %s not found".formatted(orderId));
    }

}
