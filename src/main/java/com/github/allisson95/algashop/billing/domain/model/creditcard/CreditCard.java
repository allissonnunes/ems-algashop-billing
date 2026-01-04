package com.github.allisson95.algashop.billing.domain.model.creditcard;

import java.time.Instant;
import java.util.UUID;

public class CreditCard {

    private UUID id;

    private Instant createdAt;

    private UUID customerId;

    private String lastNumbers;

    private String brand;

    private Integer expirationMonth;

    private Integer expirationYear;

    private String gatewayCode;

}
