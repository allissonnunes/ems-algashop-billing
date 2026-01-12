package br.dev.allissonnunes.algashop.billing.domain.model.creditcard;

import br.dev.allissonnunes.algashop.billing.domain.model.DomainException;

import java.util.UUID;

public class CreditCardNotFoundException extends DomainException {

    public CreditCardNotFoundException(final UUID creditCardId) {
        super("Credit card with ID %s not found".formatted(creditCardId));
    }

}
