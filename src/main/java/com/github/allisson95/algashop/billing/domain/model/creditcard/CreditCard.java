package com.github.allisson95.algashop.billing.domain.model.creditcard;

import com.github.allisson95.algashop.billing.domain.model.IdGenerator;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreditCard {

    private UUID id;

    private Instant createdAt;

    private UUID customerId;

    private String lastNumbers;

    private String brand;

    private Integer expirationMonth;

    private Integer expirationYear;

    private String gatewayCode;

    public static CreditCard brandNew(
            final UUID customerId,
            final String lastNumbers,
            final String brand,
            final Integer expirationMonth,
            final Integer expirationYear,
            final String gatewayCreditCardCode) {
        requireNonNull(customerId, "customerId cannot be null");
        requireNonNull(expirationMonth, "expirationMonth cannot be null");
        requireNonNull(expirationYear, "expirationYear cannot be null");

        if (StringUtils.isAnyBlank(lastNumbers, brand, gatewayCreditCardCode)) {
            throw new IllegalArgumentException("credit card fields cannot be blank");
        }

        return new CreditCard(
                IdGenerator.generateTimeBasedUUID(),
                Instant.now(),
                customerId,
                lastNumbers,
                brand,
                expirationMonth,
                expirationYear,
                gatewayCreditCardCode);
    }

    public void setGatewayCode(final String gatewayCode) {
        if (StringUtils.isBlank(gatewayCode)) {
            throw new IllegalArgumentException("Gateway code cannot be blank");
        }
        this.gatewayCode = gatewayCode;
    }

}
