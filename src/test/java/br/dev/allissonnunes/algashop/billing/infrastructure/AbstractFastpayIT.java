package br.dev.allissonnunes.algashop.billing.infrastructure;

import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.CreditCardProviderService;
import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.LimitedCreditCard;
import br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay.FastpayCreditCardTokenizationClient;
import br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay.FastpayCreditCardTokenizationClientConfiguration;
import br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay.FastpayTokenizationInput;
import br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay.FastpayTokenizedCreditCardModel;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ClasspathFileSource;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.extension.responsetemplating.TemplateEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.time.Year;
import java.util.Collections;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@Import(FastpayCreditCardTokenizationClientConfiguration.class)
public abstract class AbstractFastpayIT {

    protected static final String alwaysPaidCreditCardNumber = "4622943127011022";

    protected static final UUID validCustomerId = UUID.randomUUID();

    protected static WireMockServer wireMockServer;

    @Autowired
    protected FastpayCreditCardTokenizationClient fastpayCreditCardTokenizationClient;

    @Autowired
    protected CreditCardProviderService fastpayCreditCardProviderService;

//    @DynamicPropertySource
//    static void redisProperties(final DynamicPropertyRegistry registry) {
//        registry.add("algashop.integrations.payment.fastpay.host", wireMockServer::baseUrl);
//    }

    protected static void startWireMockServer() {
        final WireMockConfiguration options = options()
                .port(8788)
                .usingFilesUnderDirectory("src/test/resources/wiremock/fastpay")
                .extensions(new ResponseTemplateTransformer(
                        TemplateEngine.defaultTemplateEngine(),
                        true,
                        new ClasspathFileSource("src/test/resources/wiremock/fastpay"),
                        Collections.emptyList()
                ));
        wireMockServer = new WireMockServer(options);
        wireMockServer.start();
    }

    protected static void stopWireMockServer() {
        wireMockServer.stop();
    }

    protected LimitedCreditCard registerCreditCard() {
        final FastpayTokenizationInput input = FastpayTokenizationInput.builder()
                .number(alwaysPaidCreditCardNumber)
                .cvv("222")
                .holderName("John Doe")
                .holderDocument("123456")
                .brand("Visa")
                .expMonth(1)
                .expYear(Year.now().plusYears(5L).getValue())
                .build();
        final FastpayTokenizedCreditCardModel tokenizedCreditCard = fastpayCreditCardTokenizationClient.tokenize(input);

        return fastpayCreditCardProviderService.register(validCustomerId, tokenizedCreditCard.tokenizedCard());
    }

}
