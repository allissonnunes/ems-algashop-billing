package com.github.allisson95.algashop.billing.domain.model.invoice;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invoice {

    private UUID id;

    private String orderId;

    private UUID customerId;

    private Instant issuedAt;

    private Instant paidAt;

    private Instant canceledAt;

    private Instant expiresAt;

    private BigDecimal totalAmount;

    private InvoiceStatus status;

    private PaymentSettings paymentSettings;

    private Set<LineItem> items = new LinkedHashSet<>();

    private Payer payer;

    private String cancellationReason;

    public void markAsPaid() {

    }

    public void cancel(final String cancellationReason) {

    }

    public void assignPaymentGatewayCode(final String code) {

    }

    public void changePaymentSettings(final PaymentMethod paymentMethod, final UUID creditCardId) {

    }

    public Set<LineItem> getItems() {
        return Collections.unmodifiableSet(items);
    }

}
