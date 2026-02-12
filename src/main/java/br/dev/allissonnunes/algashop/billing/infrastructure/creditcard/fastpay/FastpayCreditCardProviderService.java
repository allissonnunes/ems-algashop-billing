package br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay;

import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.CreditCardProviderService;
import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.LimitedCreditCard;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@ConditionalOnProperty(prefix = "algashop.integrations.payment", name = "provider", havingValue = "fastpay")
@Service
class FastpayCreditCardProviderService implements CreditCardProviderService {

    @Override
    public LimitedCreditCard register(final UUID customerId, final String tokenizedCardNumber) {
        return null;
    }

    @Override
    public Optional<LimitedCreditCard> findById(final String gatewayCode) {
        return Optional.empty();
    }

    @Override
    public void delete(final String gatewayCode) {

    }

}
