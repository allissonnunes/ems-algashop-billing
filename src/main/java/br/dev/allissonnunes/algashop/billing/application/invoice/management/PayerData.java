package br.dev.allissonnunes.algashop.billing.application.invoice.management;

import lombok.Builder;

@Builder
public record PayerData(
        String fullName,
        String email,
        String document,
        String phone,
        AddressData address
) {

}
