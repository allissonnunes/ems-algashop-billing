package br.dev.allissonnunes.algashop.billing.infrastructure.payment.fake;

import br.dev.allissonnunes.algashop.billing.domain.model.invoice.PaymentMethod;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.Payment;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.PaymentGatewayService;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.PaymentRequest;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.PaymentStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.UUID;

@ConditionalOnProperty(prefix = "algashop.integrations.payment", name = "provider", havingValue = "fake")
@Service
class FakePaymentGatewayService implements PaymentGatewayService {

    @Override
    public Payment capture(final PaymentRequest request) {
        return Payment.builder()
                .gatewayCode(UUID.randomUUID().toString())
                .invoiceId(request.invoiceId())
                .method(request.method())
                .status(PaymentStatus.PAID)
                .build();
    }

    @Override
    public Payment findByCode(final String gatewayCode) {
        return Payment.builder()
                .gatewayCode(UUID.randomUUID().toString())
                .invoiceId(UUID.randomUUID())
                .method(PaymentMethod.GATEWAY_BALANCE)
                .status(PaymentStatus.PAID)
                .build();
    }

}
