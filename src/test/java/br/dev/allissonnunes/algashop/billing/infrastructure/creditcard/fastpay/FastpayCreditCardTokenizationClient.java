package br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(
        value = "/api/v1/public/tokenized-cards",
        accept = MediaType.APPLICATION_JSON_VALUE
)
public interface FastpayCreditCardTokenizationClient {

    @PostExchange(contentType = MediaType.APPLICATION_JSON_VALUE)
    FastpayTokenizedCreditCardModel tokenize(@RequestBody FastpayTokenizationInput input);

}
