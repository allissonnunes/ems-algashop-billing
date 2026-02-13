package br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay;

import lombok.Builder;

@Builder
public record FastpayCreditCardInput(String tokenizedCard, String customerCode) {

}
