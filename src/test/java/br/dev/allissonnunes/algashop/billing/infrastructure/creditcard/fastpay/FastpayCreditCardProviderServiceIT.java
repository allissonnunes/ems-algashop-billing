package br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay;

import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.LimitedCreditCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.Year;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(FastpayCreditCardTokenizationClientConfiguration.class)
class FastpayCreditCardProviderServiceIT {

    @Autowired
    private FastpayCreditCardProviderService fastpayCreditCardProviderService;

    @Autowired
    private FastpayCreditCardClient fastpayCreditCardClient;

    @Autowired
    private FastpayCreditCardTokenizationClient fastpayCreditCardTokenizationClient;

    private static final UUID validCustomerId = UUID.randomUUID();

    private static final String alwaysPaidCreditCardNumber = "4622943127011022";

    @Test
    void shouldRegisterCreditCard() {
        final LimitedCreditCard limitedCreditCard = registerCreditCard();

        assertThat(limitedCreditCard.gatewayCode()).isNotBlank();
    }

    @Test
    void shouldFindCreditCardByGatewayCode() {
        final LimitedCreditCard limitedCreditCard = registerCreditCard();

        final Optional<LimitedCreditCard> foundCreditCard = fastpayCreditCardProviderService
                .findById(limitedCreditCard.gatewayCode());

        assertThat(foundCreditCard)
                .isPresent()
                .hasValueSatisfying(creditCard -> {
                            assertThat(creditCard.gatewayCode()).isEqualTo(limitedCreditCard.gatewayCode());
                        }
                );
    }

    @Test
    void shouldDeleteCreditCard() {
        final LimitedCreditCard limitedCreditCard = registerCreditCard();

        fastpayCreditCardProviderService.delete(limitedCreditCard.gatewayCode());

        final Optional<LimitedCreditCard> foundCreditCard = fastpayCreditCardProviderService.findById(limitedCreditCard.gatewayCode());

        assertThat(foundCreditCard).isEmpty();
    }

    private LimitedCreditCard registerCreditCard() {
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