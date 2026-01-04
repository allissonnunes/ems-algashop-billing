package com.github.allisson95.algashop.billing.domain.model.invoice;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentSettings {

    private UUID id;

    private UUID creditCardId;

    private String gatewayCode;

    private PaymentMethod method;

}
