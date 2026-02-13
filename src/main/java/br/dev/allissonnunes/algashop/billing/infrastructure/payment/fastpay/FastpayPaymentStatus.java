package br.dev.allissonnunes.algashop.billing.infrastructure.payment.fastpay;

public enum FastpayPaymentStatus {
    PENDING,
    PROCESSING,
    FAILED,
    REFUNDED,
    PAID,
    CANCELED
}
