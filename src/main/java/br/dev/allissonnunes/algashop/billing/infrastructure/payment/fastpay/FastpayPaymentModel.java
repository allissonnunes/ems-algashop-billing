package br.dev.allissonnunes.algashop.billing.infrastructure.payment.fastpay;

import java.math.BigDecimal;

public record FastpayPaymentModel(
        String id,
        String referenceCode,
        String status,
        String method,
        BigDecimal totalAmount
) {

}
