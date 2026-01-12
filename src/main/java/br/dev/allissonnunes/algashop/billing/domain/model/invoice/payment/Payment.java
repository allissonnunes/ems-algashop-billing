package br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment;

import br.dev.allissonnunes.algashop.billing.domain.model.invoice.PaymentMethod;
import lombok.Builder;

import java.util.UUID;

import static br.dev.allissonnunes.algashop.billing.domain.model.Strings.requireNonBlank;
import static java.util.Objects.requireNonNull;

@Builder
public record Payment(String gatewayCode, UUID invoiceId, PaymentMethod method, PaymentStatus status) {

    public Payment {
        requireNonBlank(gatewayCode);
        requireNonNull(invoiceId);
        requireNonNull(method);
        requireNonNull(status);
    }

}
