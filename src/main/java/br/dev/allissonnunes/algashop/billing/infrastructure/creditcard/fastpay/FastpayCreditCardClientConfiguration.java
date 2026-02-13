package br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

@ImportHttpServices(group = "fastpay", types = FastpayCreditCardClient.class)
@Configuration
class FastpayCreditCardClientConfiguration {

}
