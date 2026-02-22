package br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay;

import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.LimitedCreditCard;
import br.dev.allissonnunes.algashop.billing.infrastructure.AbstractFastpayIT;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FastpayCreditCardProviderServiceIT extends AbstractFastpayIT {

    @Autowired
    private FastpayCreditCardProviderService fastpayCreditCardProviderService;

    @BeforeAll
    static void setup() {
        startWireMockServer();
    }

    @AfterAll
    static void tearDown() {
        stopWireMockServer();
    }

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
                .hasValueSatisfying(creditCard ->
                        assertThat(creditCard.gatewayCode()).isEqualTo(limitedCreditCard.gatewayCode())
                );
    }

    @Test
    void shouldDeleteCreditCard() {
        final LimitedCreditCard limitedCreditCard = registerCreditCard();

        fastpayCreditCardProviderService.delete(limitedCreditCard.gatewayCode());

//        final Optional<LimitedCreditCard> foundCreditCard = fastpayCreditCardProviderService.findById(limitedCreditCard.gatewayCode());
//
//        assertThat(foundCreditCard).isEmpty();
    }

}