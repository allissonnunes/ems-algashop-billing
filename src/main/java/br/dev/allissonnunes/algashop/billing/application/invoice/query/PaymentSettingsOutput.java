package br.dev.allissonnunes.algashop.billing.application.invoice.query;

import br.dev.allissonnunes.algashop.billing.domain.model.invoice.PaymentMethod;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PaymentSettingsOutput(
        UUID id,
        UUID creditCardId,
        PaymentMethod method
) {

}
