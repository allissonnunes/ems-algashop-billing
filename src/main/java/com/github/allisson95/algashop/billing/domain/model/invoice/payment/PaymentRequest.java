package com.github.allisson95.algashop.billing.domain.model.invoice.payment;

import com.github.allisson95.algashop.billing.domain.model.invoice.Payer;
import com.github.allisson95.algashop.billing.domain.model.invoice.PaymentMethod;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Builder
public record PaymentRequest(PaymentMethod method, BigDecimal amount, UUID invoiceId, UUID creditCardId, Payer payer) {

    public PaymentRequest {
        requireNonNull(method);
        requireNonNull(amount);
        requireNonNull(invoiceId);
        if (PaymentMethod.CREDIT_CARD.equals(method)) {
            requireNonNull(creditCardId);
        }
        requireNonNull(payer);
    }

}
