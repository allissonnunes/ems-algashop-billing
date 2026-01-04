package com.github.allisson95.algashop.billing.domain.model.creditcard;

import com.github.allisson95.algashop.billing.domain.model.IdGenerator;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

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

    @Setter(AccessLevel.PUBLIC)
    private String gatewayCode;

    public static CreditCard brandNew(
            final UUID customerId,
            final String lastNumbers,
            final String brand,
            final Integer expirationMonth,
            final Integer expirationYear,
            final String gatewayCreditCardCode) {
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

}
