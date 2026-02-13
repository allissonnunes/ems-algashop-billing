package br.dev.allissonnunes.algashop.billing.infrastructure.payment.fastpay;

import br.dev.allissonnunes.algashop.billing.domain.model.invoice.PaymentMethod;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.PaymentStatus;

public class FastpayEnumConverter {

    public static PaymentMethod convert(final FastpayPaymentMethod method) {
        return switch (method) {
            case CREDIT -> PaymentMethod.CREDIT_CARD;
            case GATEWAY_BALANCE -> PaymentMethod.GATEWAY_BALANCE;
        };
    }

    public static PaymentStatus convert(final FastpayPaymentStatus status) {
        return switch (status) {
            case PENDING -> PaymentStatus.PENDING;
            case PROCESSING -> PaymentStatus.PROCESSING;
            case FAILED, CANCELED -> PaymentStatus.FAILED;
            case REFUNDED -> PaymentStatus.REFUNDED;
            case PAID -> PaymentStatus.PAID;
        };
    }

}
