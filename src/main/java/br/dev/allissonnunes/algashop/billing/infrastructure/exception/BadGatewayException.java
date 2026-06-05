package br.dev.allissonnunes.algashop.billing.infrastructure.exception;

public class BadGatewayException extends RuntimeException {

    public BadGatewayException(final String message, final Throwable cause) {
        super(message, cause, true, false);
    }

    public static class ServerErrorException extends BadGatewayException {

        public ServerErrorException(final String message, final Throwable cause) {
            super(message, cause);
        }

    }

    public static class ClientErrorException extends BadGatewayException {

        public ClientErrorException(final String message, final Throwable cause) {
            super(message, cause);
        }

    }

}
