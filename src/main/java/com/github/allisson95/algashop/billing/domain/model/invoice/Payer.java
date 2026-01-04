package com.github.allisson95.algashop.billing.domain.model.invoice;

import lombok.*;

import static com.github.allisson95.algashop.billing.domain.model.Strings.requireNonBlank;
import static com.github.allisson95.algashop.billing.domain.model.Strings.requireValidEmail;
import static java.util.Objects.requireNonNull;

@EqualsAndHashCode
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payer {

    private String fullName;

    private String email;

    private String document;

    private String phone;

    private Address address;

    @Builder
    public Payer(final String fullName, final String email, final String document, final String phone, final Address address) {
        requireNonBlank(fullName, "fullName cannot be blank");
        requireValidEmail(email, "email must be a valid email");
        requireNonBlank(document, "document cannot be blank");
        requireNonBlank(phone, "phone cannot be blank");
        requireNonNull(address, "address cannot be null");

        this.fullName = fullName;
        this.email = email;
        this.document = document;
        this.phone = phone;
        this.address = address;
    }

}
