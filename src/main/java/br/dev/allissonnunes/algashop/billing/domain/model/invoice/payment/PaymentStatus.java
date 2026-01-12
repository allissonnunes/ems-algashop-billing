package br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment;

public enum PaymentStatus {
    PENDING,
    PROCESSING,
    FAILED,
    REFUNDED,
    PAID
}
