package br.dev.allissonnunes.algashop.billing.domain.model.invoice;

import jakarta.persistence.Embeddable;
import lombok.*;

import static br.dev.allissonnunes.algashop.billing.domain.model.Strings.requireNonBlank;

@EqualsAndHashCode
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Address {

    private String street;

    private String number;

    private String complement;

    private String neighborhood;

    private String city;

    private String state;

    private String zipCode;

    @Builder
    public Address(final String street, final String number, final String complement, final String neighborhood, final String city, final String state, final String zipCode) {
        requireNonBlank(street, "street cannot be blank");
        requireNonBlank(number, "number cannot be blank");
        requireNonBlank(neighborhood, "neighborhood cannot be blank");
        requireNonBlank(city, "city cannot be blank");
        requireNonBlank(state, "state cannot be blank");
        requireNonBlank(zipCode, "zipCode cannot be blank");

        this.street = street;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

}
