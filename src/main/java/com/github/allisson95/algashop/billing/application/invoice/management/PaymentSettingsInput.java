package com.github.allisson95.algashop.billing.application.invoice.management;

import com.github.allisson95.algashop.billing.domain.model.invoice.PaymentMethod;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PaymentSettingsInput(
        PaymentMethod method,
        UUID creditCardId
) {

}
