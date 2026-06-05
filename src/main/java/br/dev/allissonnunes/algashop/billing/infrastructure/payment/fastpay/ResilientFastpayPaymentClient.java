package br.dev.allissonnunes.algashop.billing.infrastructure.payment.fastpay;

import br.dev.allissonnunes.algashop.billing.infrastructure.exception.BadGatewayException;
import br.dev.allissonnunes.algashop.billing.infrastructure.exception.GatewayTimeoutException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import java.net.SocketTimeoutException;

@ConditionalOnProperty(prefix = "algashop.integrations.payment", name = "provider", havingValue = "fastpay")
@Component
@RequiredArgsConstructor
class ResilientFastpayPaymentClient {

    private final FastpayPaymentClient paymentClient;

    @CircuitBreaker(name = "fastpayCB")
    FastpayPaymentModel capture(final FastpayPaymentInput input) {
        try {
            if (Math.random() < 0.5) {
                throw new RestClientException("Teste", new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
            }
            return paymentClient.capture(input);
        } catch (final RestClientException e) {
            throw translateException(e);
        }
    }

    @Retry(name = "fastpayRetry")
    FastpayPaymentModel findById(final String paymentId) {
        try {
            return paymentClient.findById(paymentId);
        } catch (final RestClientException e) {
            throw translateException(e);
        }
    }

    private RuntimeException translateException(final RestClientException e) {
        if (e.getCause() instanceof SocketTimeoutException || e instanceof ResourceAccessException) {
            return new GatewayTimeoutException("Fastpay API Timeout", e);
        }

        if (e instanceof HttpClientErrorException) {
            return new BadGatewayException.ClientErrorException("Fastpay API Client Error", e);
        }

        if (e instanceof HttpServerErrorException) {
            return new BadGatewayException.ServerErrorException("Fastpay API Internal Error", e);
        }

        return new BadGatewayException("Fastpay API Bad Gateway", e);
    }

}
