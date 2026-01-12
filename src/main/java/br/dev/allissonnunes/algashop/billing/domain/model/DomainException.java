package br.dev.allissonnunes.algashop.billing.domain.model;

public class DomainException extends RuntimeException {

    public DomainException(final String message) {
        this(message, null);
    }

    public DomainException(final String message, final Throwable cause) {
        super(message, cause, true, false);
    }

}
