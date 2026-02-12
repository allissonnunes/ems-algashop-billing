package br.dev.allissonnunes.algashop.billing.domain.model.creditcard;

import java.util.Optional;
import java.util.UUID;

public interface CreditCardProviderService {

    LimitedCreditCard register(UUID customerId, String tokenizedCardNumber);

    Optional<LimitedCreditCard> findById(String gatewayCode);

    void delete(String gatewayCode);

}
