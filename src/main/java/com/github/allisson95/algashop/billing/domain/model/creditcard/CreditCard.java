package com.github.allisson95.algashop.billing.domain.model.creditcard;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

}
