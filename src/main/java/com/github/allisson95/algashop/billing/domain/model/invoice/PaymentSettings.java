package com.github.allisson95.algashop.billing.domain.model.invoice;

import java.util.UUID;

public class PaymentSettings {

    private UUID id;

    private UUID creditCardId;

    private String gatewayCode;

    private PaymentMethod method;

}
