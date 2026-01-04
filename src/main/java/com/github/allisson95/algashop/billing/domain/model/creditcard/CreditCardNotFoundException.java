package com.github.allisson95.algashop.billing.domain.model.creditcard;

import com.github.allisson95.algashop.billing.domain.model.DomainException;

import java.util.UUID;

public class CreditCardNotFoundException extends DomainException {

    public CreditCardNotFoundException(final UUID creditCardId) {
        super("Credit card with ID %s not found".formatted(creditCardId));
    }

}
