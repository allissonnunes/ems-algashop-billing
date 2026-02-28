package br.dev.allissonnunes.algashop.billing.infrastructure;

import br.dev.allissonnunes.algashop.billing.TestcontainersConfiguration;
import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.CreditCardProviderService;
import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.LimitedCreditCard;
import br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay.FastpayCreditCardTokenizationClient;
import br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay.FastpayCreditCardTokenizationClientConfiguration;
import br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay.FastpayTokenizationInput;
import br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay.FastpayTokenizedCreditCardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import java.time.Year;
import java.util.UUID;

@EnableWireMock({
        @ConfigureWireMock(
                name = "fastpay",
                filesUnderClasspath = "wiremock/fastpay",
                baseUrlProperties = "algashop.integrations.payment.fastpay.host"
        )
})
@Import({ FastpayCreditCardTokenizationClientConfiguration.class, TestcontainersConfiguration.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public abstract class AbstractFastpayIT {

    protected static final String alwaysPaidCreditCardNumber = "4622943127011022";

    protected static final UUID validCustomerId = UUID.randomUUID();

    @Autowired
    protected FastpayCreditCardTokenizationClient fastpayCreditCardTokenizationClient;

    @Autowired
    protected CreditCardProviderService fastpayCreditCardProviderService;

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
