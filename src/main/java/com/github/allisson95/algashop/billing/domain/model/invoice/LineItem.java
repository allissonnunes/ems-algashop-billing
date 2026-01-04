package com.github.allisson95.algashop.billing.domain.model.invoice;

import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LineItem {

    private Integer number;

    private String name;

    private BigDecimal amount;

}
