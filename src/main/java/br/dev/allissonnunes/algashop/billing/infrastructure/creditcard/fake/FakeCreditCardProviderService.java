package br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fake;

import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.CreditCardProviderService;
import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.LimitedCreditCard;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Optional;
import java.util.UUID;

@ConditionalOnProperty(prefix = "algashop.integrations.payment", name = "provider", havingValue = "fake")
@Service
class FakeCreditCardProviderService implements CreditCardProviderService {

    @Override
    public LimitedCreditCard register(final UUID customerId, final String tokenizedCardNumber) {
        return fakeCard();
    }

    @Override
    public Optional<LimitedCreditCard> findById(final String gatewayCode) {
        return Optional.of(fakeCard());
    }

    @Override
    public void delete(final String gatewayCode) {

    }

    private LimitedCreditCard fakeCard() {
        return LimitedCreditCard.builder()
                .lastNumbers("1234")
                .brand("Visa")
                .expirationMonth(1)
                .expirationYear(Year.now().getValue() + 5)
                .gatewayCode(UUID.randomUUID().toString())
                .build();
    }

}
