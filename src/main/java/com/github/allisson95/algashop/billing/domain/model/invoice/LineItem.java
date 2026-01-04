package com.github.allisson95.algashop.billing.domain.model.invoice;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;

import static com.github.allisson95.algashop.billing.domain.model.Strings.requireNonBlank;
import static java.util.Objects.requireNonNull;

@EqualsAndHashCode
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class LineItem {

    private Integer number;

    private String name;

    private BigDecimal amount;

    @Builder
    public LineItem(final Integer number, final String name, final BigDecimal amount) {
        requireNonNull(number, "number cannot be null");
        requireNonBlank(name, "name cannot be blank");
        requireNonNull(amount, "amount cannot be null");

        if (number <= 0) {
            throw new IllegalArgumentException("number cannot be negative or zero");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount cannot be negative or zero");
        }

        this.number = number;
        this.name = name;
        this.amount = amount;
    }

}
