package br.dev.allissonnunes.algashop.billing.infrastructure;

import br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay.FastpayCreditCardClient;
import br.dev.allissonnunes.algashop.billing.infrastructure.payment.fastpay.FastpayPaymentClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

@ImportHttpServices(
        group = "fastpay",
        types = { FastpayCreditCardClient.class, FastpayPaymentClient.class }
)
@Configuration
class FastpayClientConfiguration {

}
