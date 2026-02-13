package br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay;

public record FastpayCreditCardResponse(
        String id,
        String lastNumbers,
        Integer expMonth,
        Integer expYear,
        String brand
) {

}
