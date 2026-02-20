package br.dev.allissonnunes.algashop.billing.presentation;

import jakarta.validation.constraints.NotBlank;

public record TokenizedCreditCardRequest(
        @NotBlank
        String tokenizedCard
) {

}
