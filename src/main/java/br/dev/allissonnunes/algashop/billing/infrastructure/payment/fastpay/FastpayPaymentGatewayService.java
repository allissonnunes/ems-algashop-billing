package br.dev.allissonnunes.algashop.billing.infrastructure.payment.fastpay;

import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.Payment;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.PaymentGatewayService;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.PaymentRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(prefix = "algashop.integrations.payment", name = "provider", havingValue = "fastpay")
@Service
class FastpayPaymentGatewayService implements PaymentGatewayService {

    @Override
    public Payment capture(final PaymentRequest request) {
        return null;
    }

    @Override
    public Payment findByCode(final String gatewayCode) {
        return null;
    }

}
