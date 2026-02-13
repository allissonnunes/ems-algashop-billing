package br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay;

import java.time.Instant;

public record FastpayTokenizedCreditCardModel(String tokenizedCard, Instant expiresAt) {

}
