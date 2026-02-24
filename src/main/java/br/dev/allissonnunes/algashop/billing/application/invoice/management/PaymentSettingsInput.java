package br.dev.allissonnunes.algashop.billing.application.invoice.management;

import br.dev.allissonnunes.algashop.billing.domain.model.invoice.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PaymentSettingsInput(
        @NotNull
        PaymentMethod method,
        UUID creditCardId
) {

}
