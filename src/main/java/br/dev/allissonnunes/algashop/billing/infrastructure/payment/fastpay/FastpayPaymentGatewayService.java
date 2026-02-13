package br.dev.allissonnunes.algashop.billing.infrastructure.payment.fastpay;

import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.CreditCard;
import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.CreditCardNotFoundException;
import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.CreditCardRepository;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.Address;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.Payer;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.Payment;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.PaymentGatewayService;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.UUID;

@ConditionalOnProperty(prefix = "algashop.integrations.payment", name = "provider", havingValue = "fastpay")
@Service
@RequiredArgsConstructor
class FastpayPaymentGatewayService implements PaymentGatewayService {

    private final FastpayPaymentClient paymentClient;

    private final CreditCardRepository creditCardRepository;

    @Override
    public Payment capture(final PaymentRequest request) {
        final FastpayPaymentInput input = convertToInput(request);

        final FastpayPaymentModel paymentResponse;
        try {
            paymentResponse = paymentClient.capture(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return convertToPayment(paymentResponse);
    }

    @Override
    public Payment findByCode(final String gatewayCode) {
        final FastpayPaymentModel paymentResponse;
        try {
            paymentResponse = paymentClient.findById(gatewayCode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return convertToPayment(paymentResponse);
    }

    private FastpayPaymentInput convertToInput(final PaymentRequest request) {
        final Payer payer = request.payer();
        final Address address = payer.getAddress();

        final FastpayPaymentInput.FastpayPaymentInputBuilder builder = FastpayPaymentInput.builder();
        builder.referenceCode(request.invoiceId().toString());
        builder.totalAmount(request.amount());

        switch (request.method()) {
            case CREDIT_CARD -> {
                builder.method(FastpayPaymentMethod.CREDIT.name());
                final CreditCard creditCard = creditCardRepository.findById(request.creditCardId())
                        .orElseThrow(() -> new CreditCardNotFoundException(request.creditCardId()));
                builder.creditCardId(creditCard.getGatewayCode());
            }
            case GATEWAY_BALANCE -> builder.method(FastpayPaymentMethod.GATEWAY_BALANCE.name());
        }

        builder.fullName(payer.getFullName());
        builder.document(payer.getDocument());
        builder.phone(payer.getPhone());
        builder.addressLine1(address.getStreet() + ", " + address.getNumber());
        builder.addressLine2(address.getComplement());
        builder.zipCode(address.getZipCode());
        builder.replyToUrl("https://example.com/fastpay/callback");
        return builder.build();
    }

    private Payment convertToPayment(final FastpayPaymentModel paymentResponse) {
        final Payment.PaymentBuilder builder = Payment.builder();
        builder.gatewayCode(paymentResponse.id());
        builder.invoiceId(UUID.fromString(paymentResponse.referenceCode()));

        final FastpayPaymentMethod fastpayPaymentMethod;
        try {
            fastpayPaymentMethod = FastpayPaymentMethod.valueOf(paymentResponse.method());
        } catch (final IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown payment method: " + paymentResponse.method());
        }

        final FastpayPaymentStatus fastpayPaymentStatus;
        try {
            fastpayPaymentStatus = FastpayPaymentStatus.valueOf(paymentResponse.status());
        } catch (final IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown payment status: " + paymentResponse.status());
        }

        builder.method(FastpayEnumConverter.convert(fastpayPaymentMethod));
        builder.status(FastpayEnumConverter.convert(fastpayPaymentStatus));

        return builder.build();
    }

}
