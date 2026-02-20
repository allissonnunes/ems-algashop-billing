package br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.web.service.registry.ImportHttpServices;

@ImportHttpServices(group = "fastpay-public", types = { FastpayCreditCardTokenizationClient.class })
@TestConfiguration
public class FastpayCreditCardTokenizationClientConfiguration {

}
