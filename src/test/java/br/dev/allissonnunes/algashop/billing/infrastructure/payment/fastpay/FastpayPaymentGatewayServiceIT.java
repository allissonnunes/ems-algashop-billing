package br.dev.allissonnunes.algashop.billing.infrastructure.payment.fastpay;

import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.CreditCard;
import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.CreditCardRepository;
import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.LimitedCreditCard;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.InvoiceTestDataBuilder;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.PaymentMethod;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.Payment;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.PaymentRequest;
import br.dev.allissonnunes.algashop.billing.infrastructure.AbstractFastpayIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FastpayPaymentGatewayServiceIT extends AbstractFastpayIT {

    @Autowired
    private FastpayPaymentGatewayService fastpayPaymentGatewayService;

    @Autowired
    private CreditCardRepository creditCardRepository;

    private static final UUID validCustomerId = UUID.randomUUID();

    @Test
    void shouldProcessPaymentWithCreditCard() {
        final LimitedCreditCard limitedCreditCard = registerCreditCard();

        final CreditCard creditCard = CreditCard.brandNew(
                validCustomerId,
                limitedCreditCard.lastNumbers(),
                limitedCreditCard.brand(),
                limitedCreditCard.expirationMonth(),
                limitedCreditCard.expirationYear(),
                limitedCreditCard.gatewayCode()
        );

        creditCardRepository.save(creditCard);

        final UUID invoiceId = UUID.randomUUID();
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .method(PaymentMethod.CREDIT_CARD)
                .amount(new BigDecimal("1000.00"))
                .invoiceId(invoiceId)
                .creditCardId(creditCard.getId())
                .payer(InvoiceTestDataBuilder.aPayer())
                .build();

        final Payment processedPayment = fastpayPaymentGatewayService.capture(paymentRequest);

        assertThat(processedPayment.invoiceId()).isEqualTo(invoiceId);
    }

}