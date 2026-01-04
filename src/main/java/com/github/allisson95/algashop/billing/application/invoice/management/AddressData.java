package com.github.allisson95.algashop.billing.application.invoice.management;

import lombok.Builder;

@Builder
public record AddressData(
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String zipCode
) {

}
