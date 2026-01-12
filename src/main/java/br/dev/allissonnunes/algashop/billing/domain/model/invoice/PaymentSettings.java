package br.dev.allissonnunes.algashop.billing.domain.model.invoice;

import br.dev.allissonnunes.algashop.billing.domain.model.DomainException;
import br.dev.allissonnunes.algashop.billing.domain.model.IdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class PaymentSettings {

    @Id
    private UUID id;

    private UUID creditCardId;

    private String gatewayCode;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PACKAGE)
    @OneToOne(mappedBy = Invoice_.PAYMENT_SETTINGS)
    private Invoice invoice;

    static PaymentSettings brandNew(final PaymentMethod paymentMethod, final UUID creditCardId) {
        requireNonNull(paymentMethod, "paymentMethod cannot be null");
        if (PaymentMethod.CREDIT_CARD.equals(paymentMethod)) {
            requireNonNull(creditCardId, "creditCardId cannot be null");
        }
        return new PaymentSettings(IdGenerator.generateTimeBasedUUID(), creditCardId, null, paymentMethod, null);
    }

    void assignGatewayCode(final String paymentGatewayCode) {
        if (StringUtils.isBlank(paymentGatewayCode)) {
            throw new IllegalArgumentException("Payment gateway code cannot be blank");
        }
        if (this.getGatewayCode() != null) {
            throw new DomainException("Payment gateway code already assigned");
        }
        this.setGatewayCode(paymentGatewayCode);
    }

}
