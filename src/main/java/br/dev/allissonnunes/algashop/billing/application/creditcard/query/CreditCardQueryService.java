package br.dev.allissonnunes.algashop.billing.application.creditcard.query;

import java.util.List;
import java.util.UUID;

public interface CreditCardQueryService {

    CreditCardOutput findById(UUID customerId, UUID creditCardId);

    List<CreditCardOutput> findByCustomer(UUID customerId);

}
