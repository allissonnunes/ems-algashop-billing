package br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay;

import lombok.Builder;

@Builder
public record FastpayTokenizationInput(
        String number,
        String cvv,
        String holderName,
        String holderDocument,
        String brand,
        Integer expMonth,
        Integer expYear
) {

}
