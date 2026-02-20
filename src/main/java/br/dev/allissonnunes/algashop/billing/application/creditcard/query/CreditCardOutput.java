package br.dev.allissonnunes.algashop.billing.application.creditcard.query;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CreditCardOutput(
        UUID id,
        String lastNumbers,
        Integer expirationMonth,
        Integer expirationYear,
        String brand
) {

}
