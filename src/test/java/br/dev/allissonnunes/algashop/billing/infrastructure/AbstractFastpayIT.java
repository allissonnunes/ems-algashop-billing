package br.dev.allissonnunes.algashop.billing.infrastructure;

import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.CreditCardProviderService;
import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.LimitedCreditCard;
import br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay.FastpayCreditCardTokenizationClient;
import br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay.FastpayCreditCardTokenizationClientConfiguration;
import br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay.FastpayTokenizationInput;
import br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay.FastpayTokenizedCreditCardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.time.Year;
import java.util.UUID;

@Import(FastpayCreditCardTokenizationClientConfiguration.class)
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
