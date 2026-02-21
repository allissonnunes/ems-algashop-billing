package br.dev.allissonnunes.algashop.billing.infrastructure.payment.fastpay.webhook;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record FastpayPaymentWebhookEvent(
        @NotBlank
        String paymentId,
        @NotBlank
        String referenceCode,
        @NotBlank
        String status,
        @NotBlank
        String method,
        @NotNull
        Instant notifiedAt
) {

}
