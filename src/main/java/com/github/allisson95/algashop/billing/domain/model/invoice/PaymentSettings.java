package com.github.allisson95.algashop.billing.domain.model.invoice;

import com.github.allisson95.algashop.billing.domain.model.IdGenerator;
import lombok.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentSettings {

    private UUID id;

    private UUID creditCardId;

    private String gatewayCode;

    private PaymentMethod method;

    public static PaymentSettings brandNew(final PaymentMethod paymentMethod, final UUID creditCardId) {
        return new PaymentSettings(IdGenerator.generateTimeBasedUUID(), creditCardId, null, paymentMethod);
    }

}
