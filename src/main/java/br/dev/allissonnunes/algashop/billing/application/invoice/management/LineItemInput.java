package br.dev.allissonnunes.algashop.billing.application.invoice.management;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record LineItemInput(
        @NotBlank
        String name,
        @NotNull
        @Positive
        BigDecimal amount) {

}
