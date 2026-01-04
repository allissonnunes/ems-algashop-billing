package com.github.allisson95.algashop.billing.domain.model.invoice;

import lombok.*;

@EqualsAndHashCode
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Address {

    private String street;

    private String number;

    private String complement;

    private String neighborhood;

    private String city;

    private String state;

    private String zipCode;

}
