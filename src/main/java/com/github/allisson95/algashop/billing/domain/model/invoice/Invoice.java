package com.github.allisson95.algashop.billing.domain.model.invoice;

import com.github.allisson95.algashop.billing.domain.model.DomainException;
import com.github.allisson95.algashop.billing.domain.model.IdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Invoice extends AbstractAggregateRoot<Invoice> {

    @Id
    private UUID id;

    private String orderId;

    private UUID customerId;

    private Instant issuedAt;

    private Instant paidAt;

    private Instant canceledAt;

    private Instant expiresAt;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private PaymentSettings paymentSettings;

    @ElementCollection
    @CollectionTable(name = "invoice_line_item", joinColumns = @JoinColumn(name = "invoice_id"))
    private Set<LineItem> items = new LinkedHashSet<>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fullName", column = @Column(name = "payer_full_name")),
            @AttributeOverride(name = "email", column = @Column(name = "payer_email")),
            @AttributeOverride(name = "document", column = @Column(name = "payer_document")),
            @AttributeOverride(name = "phone", column = @Column(name = "payer_phone")),
            @AttributeOverride(name = "address.street", column = @Column(name = "payer_address_street")),
            @AttributeOverride(name = "address.number", column = @Column(name = "payer_address_number")),
            @AttributeOverride(name = "address.complement", column = @Column(name = "payer_address_complement")),
            @AttributeOverride(name = "address.neighborhood", column = @Column(name = "payer_address_neighborhood")),
            @AttributeOverride(name = "address.city", column = @Column(name = "payer_address_city")),
            @AttributeOverride(name = "address.state", column = @Column(name = "payer_address_state")),
            @AttributeOverride(name = "address.zipCode", column = @Column(name = "payer_address_zip_code")),
    })
    private Payer payer;

    private String cancellationReason;

    public static Invoice issue(final String orderId, final UUID customerId, final Payer payer, final Set<LineItem> items) {
        requireNonNull(customerId, "customerId cannot be null");
        requireNonNull(payer, "payer cannot be null");
        requireNonNull(items, "items cannot be null");

        if (StringUtils.isBlank(orderId)) {
            throw new IllegalArgumentException("orderId cannot be blank");
        }

        if (items.isEmpty()) {
            throw new IllegalArgumentException("items cannot be empty");
        }

        final BigDecimal totalAmount = items.stream()
                .map(LineItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final Invoice invoice = new Invoice(
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

        invoice.registerEvent(new InvoiceIssuedEvent(invoice.getId(), invoice.getCustomerId(), invoice.getOrderId(), invoice.getIssuedAt()));

        return invoice;
    }

    public void markAsPaid() {
        if (!this.isUnpaid()) {
            throw new DomainException("Invoice %s with status %s cannot be marked as paid".formatted(this.id, this.status.toString().toLowerCase(Locale.ROOT)));
        }
        this.setPaidAt(Instant.now());
        this.setStatus(InvoiceStatus.PAID);
        super.registerEvent(new InvoicePaidEvent(this.getId(), this.getCustomerId(), this.getOrderId(), this.getPaidAt()));
    }

    public void cancel(final String cancellationReason) {
        if (this.isCanceled()) {
            throw new DomainException("Invoice %s is already canceled".formatted(this.id));
        }
        this.setCancellationReason(cancellationReason);
        this.setCanceledAt(Instant.now());
        this.setStatus(InvoiceStatus.CANCELED);
        super.registerEvent(new InvoiceCanceledEvent(this.getId(), this.getCustomerId(), this.getOrderId(), this.getCanceledAt()));
    }

    public void assignPaymentGatewayCode(final String code) {
        if (!this.isUnpaid()) {
            throw new DomainException("Invoice %s with status %s cannot be edited".formatted(this.id, this.status.toString().toLowerCase(Locale.ROOT)));
        }
        if (isNull(this.getPaymentSettings())) {
            throw new DomainException("Invoice %s has no payment settings assigned".formatted(this.id));
        }
        this.getPaymentSettings().assignGatewayCode(code);
    }

    public void changePaymentSettings(final PaymentMethod paymentMethod, final UUID creditCardId) {
        if (!this.isUnpaid()) {
            throw new DomainException("Invoice %s with status %s cannot be edited".formatted(this.id, this.status.toString().toLowerCase(Locale.ROOT)));
        }
        final var paymentSettings = PaymentSettings.brandNew(paymentMethod, creditCardId);
        paymentSettings.setInvoice(this);
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
