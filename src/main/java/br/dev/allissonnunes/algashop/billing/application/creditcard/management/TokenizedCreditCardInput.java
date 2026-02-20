package br.dev.allissonnunes.algashop.billing.application.creditcard.management;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TokenizedCreditCardInput(
        @NotNull
        UUID customerId,
        @NotBlank
        String tokenizedCard
) {

}
