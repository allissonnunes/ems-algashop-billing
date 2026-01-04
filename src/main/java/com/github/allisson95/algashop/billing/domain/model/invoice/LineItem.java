package com.github.allisson95.algashop.billing.domain.model.invoice;

import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode
public class LineItem {

    private Integer number;

    private String name;

    private BigDecimal amount;

}
