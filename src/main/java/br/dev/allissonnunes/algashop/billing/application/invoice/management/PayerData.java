package br.dev.allissonnunes.algashop.billing.application.invoice.management;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PayerData(
        @NotBlank
        String fullName,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String document,
        @NotNull
        String phone,
        @NotNull
        @Valid
        AddressData address
) {

}
