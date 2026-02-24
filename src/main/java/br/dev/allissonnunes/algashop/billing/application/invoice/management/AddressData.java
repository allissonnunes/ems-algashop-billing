package br.dev.allissonnunes.algashop.billing.application.invoice.management;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AddressData(
        @NotBlank
        String street,
        @NotBlank
        String number,
        String complement,
        @NotBlank
        String neighborhood,
        @NotBlank
        String city,
        @NotBlank
        String state,
        @NotBlank
        String zipCode
) {

}
