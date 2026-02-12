package br.dev.allissonnunes.algashop.billing.domain.model.creditcard;

import lombok.Builder;

@Builder
public record LimitedCreditCard(
        String lastNumbers,
        String brand,
        Integer expirationMonth,
        Integer expirationYear,
        String gatewayCode
) {

}
