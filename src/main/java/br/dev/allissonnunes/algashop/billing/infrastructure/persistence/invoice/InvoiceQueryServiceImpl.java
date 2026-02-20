package br.dev.allissonnunes.algashop.billing.infrastructure.persistence.invoice;

import br.dev.allissonnunes.algashop.billing.application.invoice.management.AddressData;
import br.dev.allissonnunes.algashop.billing.application.invoice.management.PayerData;
import br.dev.allissonnunes.algashop.billing.application.invoice.query.InvoiceOutput;
import br.dev.allissonnunes.algashop.billing.application.invoice.query.InvoiceQueryService;
import br.dev.allissonnunes.algashop.billing.application.invoice.query.PaymentSettingsOutput;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class InvoiceQueryServiceImpl implements InvoiceQueryService {

    private final InvoiceRepository invoiceRepository;

    @Override
    public InvoiceOutput findByOrderId(final String orderId) {
        return invoiceRepository.findByOrderId(orderId)
                .map(this::convert)
                .orElseThrow(() -> new InvoiceNotFoundException(orderId));
    }

    private InvoiceOutput convert(final Invoice invoice) {
        final Payer payer = invoice.getPayer();
        final PaymentSettings paymentSettings = invoice.getPaymentSettings();
        final PayerData payerData = convert(payer);
        final PaymentSettingsOutput paymentSettingsOutput = convert(paymentSettings);

        return InvoiceOutput.builder()
                .id(invoice.getId())
                .orderId(invoice.getOrderId())
                .customerId(invoice.getCustomerId())
                .issuedAt(invoice.getIssuedAt())
                .paidAt(invoice.getPaidAt())
                .canceledAt(invoice.getCanceledAt())
                .expiresAt(invoice.getExpiresAt())
                .totalAmount(invoice.getTotalAmount())
                .status(invoice.getStatus())
                .payer(payerData)
                .paymentSettings(paymentSettingsOutput)
                .build();
    }

    private PaymentSettingsOutput convert(final PaymentSettings paymentSettings) {
        return PaymentSettingsOutput.builder()
                .id(paymentSettings.getId())
                .creditCardId(paymentSettings.getCreditCardId())
                .method(paymentSettings.getMethod())
                .build();
    }

    private PayerData convert(final Payer payer) {
        final Address address = payer.getAddress();
        final AddressData addressData = convert(address);

        return PayerData.builder()
                .fullName(payer.getFullName())
                .email(payer.getEmail())
                .document(payer.getDocument())
                .phone(payer.getPhone())
                .address(addressData)
                .build();
    }

    private AddressData convert(final Address address) {
        return AddressData.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .complement(address.getComplement())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }

}
