package br.dev.allissonnunes.algashop.billing.application.invoice.management;

import br.dev.allissonnunes.algashop.billing.domain.model.invoice.PaymentMethod;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PaymentSettingsInput(
        PaymentMethod method,
        UUID creditCardId
) {

}
