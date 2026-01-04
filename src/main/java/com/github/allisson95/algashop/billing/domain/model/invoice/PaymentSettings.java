package com.github.allisson95.algashop.billing.domain.model.invoice;

import com.github.allisson95.algashop.billing.domain.model.IdGenerator;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

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

    void assignGatewayCode(final String paymentGatewayCode) {
        if (StringUtils.isBlank(paymentGatewayCode)) {
            throw new IllegalArgumentException("Payment gateway code cannot be blank");
        }
        this.setGatewayCode(paymentGatewayCode);
    }

}
