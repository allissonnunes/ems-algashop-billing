package br.dev.allissonnunes.algashop.billing.domain.model.creditcard;

import br.dev.allissonnunes.algashop.billing.domain.model.DomainEntityNotFoundException;

import java.util.UUID;

public class CreditCardNotFoundException extends DomainEntityNotFoundException {

    public CreditCardNotFoundException(final UUID creditCardId) {
        super("Credit card with ID %s not found".formatted(creditCardId));
    }

}
