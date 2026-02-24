package br.dev.allissonnunes.algashop.billing.infrastructure.creditcard.fastpay;

import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.CreditCardProviderService;
import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.LimitedCreditCard;
import br.dev.allissonnunes.algashop.billing.infrastructure.exception.BadGatewayException;
import br.dev.allissonnunes.algashop.billing.infrastructure.exception.GatewayTimeoutException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.Optional;
import java.util.UUID;

@ConditionalOnProperty(prefix = "algashop.integrations.payment", name = "provider", havingValue = "fastpay")
@Service
@RequiredArgsConstructor
class FastpayCreditCardProviderService implements CreditCardProviderService {

    private final FastpayCreditCardClient fastpayCreditCardClient;

    @Override
    public LimitedCreditCard register(final UUID customerId, final String tokenizedCardNumber) {
        final FastpayCreditCardInput input = FastpayCreditCardInput.builder()
                .tokenizedCard(tokenizedCardNumber)
                .customerCode(customerId.toString())
                .build();

        final FastpayCreditCardResponse response;
        try {
            response = fastpayCreditCardClient.create(input);
        } catch (final ResourceAccessException e) {
            throw new GatewayTimeoutException("Fastpay API Timeout", e);
        } catch (final HttpClientErrorException e) {
            throw new BadGatewayException("Fastpay API Bad Gateway", e);
        }

        return convertToLimitedCreditCard(response);
    }

    @Override
    public Optional<LimitedCreditCard> findById(final String gatewayCode) {
        final FastpayCreditCardResponse response;
        try {
            response = fastpayCreditCardClient.findById(gatewayCode);
        } catch (final HttpClientErrorException.NotFound _) {
            return Optional.empty();
        } catch (final ResourceAccessException e) {
            throw new GatewayTimeoutException("Fastpay API Timeout", e);
        } catch (final HttpClientErrorException e) {
            throw new BadGatewayException("Fastpay API Bad Gateway", e);
        }
        return Optional.of(convertToLimitedCreditCard(response));
    }

    @Override
    public void delete(final String gatewayCode) {
        try {
            fastpayCreditCardClient.delete(gatewayCode);
        } catch (final ResourceAccessException e) {
            throw new GatewayTimeoutException("Fastpay API Timeout", e);
        } catch (final HttpClientErrorException e) {
            throw new BadGatewayException("Fastpay API Bad Gateway", e);
        }
    }

    private LimitedCreditCard convertToLimitedCreditCard(final FastpayCreditCardResponse response) {
        return LimitedCreditCard.builder()
                .gatewayCode(response.id())
                .lastNumbers(response.lastNumbers())
                .expirationMonth(response.expMonth())
                .expirationYear(response.expYear())
                .brand(response.brand())
                .build();
    }

}
