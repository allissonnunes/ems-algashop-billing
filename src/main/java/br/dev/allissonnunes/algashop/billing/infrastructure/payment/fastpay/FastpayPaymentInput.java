package br.dev.allissonnunes.algashop.billing.infrastructure.payment.fastpay;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record FastpayPaymentInput(
        String referenceCode,
        BigDecimal totalAmount,
        String method,
        String creditCardId,
        String fullName,
        String document,
        String phone,
        String addressLine1,
        String addressLine2,
        String zipCode,
        String replyToUrl
) {

}
