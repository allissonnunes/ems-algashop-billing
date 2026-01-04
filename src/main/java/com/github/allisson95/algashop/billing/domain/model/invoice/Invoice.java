package com.github.allisson95.algashop.billing.domain.model.invoice;

import com.github.allisson95.algashop.billing.domain.model.IdGenerator;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    public static Invoice issue(final String orderId, final UUID customerId, final Payer payer, final Set<LineItem> items) {
        final BigDecimal totalAmount = items.stream()
                .map(LineItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new Invoice(
                IdGenerator.generateTimeBasedUUID(),
                orderId,
                customerId,
                Instant.now(),
                null,
                null,
                Instant.now().plus(3, ChronoUnit.DAYS),
                totalAmount,
                InvoiceStatus.UNPAID,
                null,
                items,
                payer,
                null);
    }

    public void markAsPaid() {

    }

    public void cancel(final String cancellationReason) {

    }

    public void assignPaymentGatewayCode(final String code) {

    }

    public void changePaymentSettings(final PaymentMethod paymentMethod, final UUID creditCardId) {
        final var paymentSettings = PaymentSettings.brandNew(paymentMethod, creditCardId);
        this.setPaymentSettings(paymentSettings);
    }

    public Set<LineItem> getItems() {
        return Collections.unmodifiableSet(items);
    }

}
