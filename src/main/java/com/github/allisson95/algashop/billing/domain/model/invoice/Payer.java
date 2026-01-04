package com.github.allisson95.algashop.billing.domain.model.invoice;

import lombok.*;

@EqualsAndHashCode
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Payer {

    private String fullName;

    private String email;

    private String document;

    private String phone;

    private Address address;

}
