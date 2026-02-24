package br.dev.allissonnunes.algashop.billing.infrastructure.exception;

public class BadGatewayException extends RuntimeException {

    public BadGatewayException(final String message, final Throwable cause) {
        super(message, cause, true, false);
    }

}
