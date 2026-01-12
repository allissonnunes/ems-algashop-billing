package br.dev.allissonnunes.algashop.billing.infrastructure.payment;

import br.dev.allissonnunes.algashop.billing.domain.model.invoice.PaymentMethod;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.Payment;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.PaymentGatewayService;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.PaymentRequest;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.PaymentStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class FakePaymentGatewayServiceImpl implements PaymentGatewayService {

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
