package com.github.allisson95.algashop.billing.domain.model.invoice;

import com.github.allisson95.algashop.billing.domain.model.DomainException;
import com.github.allisson95.algashop.billing.domain.model.IdGenerator;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
        if (!this.isUnpaid()) {
            throw new DomainException("Invoice %s with status %s cannot be marked as paid".formatted(this.id, this.status.toString().toLowerCase(Locale.ROOT)));
        }
        this.setPaidAt(Instant.now());
        this.setStatus(InvoiceStatus.PAID);
    }

    public void cancel(final String cancellationReason) {
        if (this.isCanceled()) {
            throw new DomainException("Invoice %s is already canceled".formatted(this.id));
        }
        this.setCancellationReason(cancellationReason);
        this.setCanceledAt(Instant.now());
        this.setStatus(InvoiceStatus.CANCELED);
    }

    public void assignPaymentGatewayCode(final String code) {
        if (!this.isUnpaid()) {
            throw new DomainException("Invoice %s with status %s cannot be edited".formatted(this.id, this.status.toString().toLowerCase(Locale.ROOT)));
        }
        this.getPaymentSettings().assignGatewayCode(code);
    }

    public void changePaymentSettings(final PaymentMethod paymentMethod, final UUID creditCardId) {
        if (!this.isUnpaid()) {
            throw new DomainException("Invoice %s with status %s cannot be edited".formatted(this.id, this.status.toString().toLowerCase(Locale.ROOT)));
        }
        final var paymentSettings = PaymentSettings.brandNew(paymentMethod, creditCardId);
        this.setPaymentSettings(paymentSettings);
    }

    public boolean isCanceled() {
        return InvoiceStatus.CANCELED.equals(this.status);
    }

    public boolean isUnpaid() {
        return InvoiceStatus.UNPAID.equals(this.status);
    }

    public boolean isPaid() {
        return InvoiceStatus.PAID.equals(this.status);
    }

    public Set<LineItem> getItems() {
        return Collections.unmodifiableSet(items);
    }

}
