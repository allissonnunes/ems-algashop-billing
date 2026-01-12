package br.dev.allissonnunes.algashop.billing.domain.model.invoice;

import br.dev.allissonnunes.algashop.billing.domain.model.DomainException;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoicingService {

    private final InvoiceRepository invoiceRepository;

    public Invoice issue(final String orderId, final UUID customerId, final Payer payer, final Set<LineItem> items) {
        if (this.invoiceRepository.existsByOrderId(orderId)) {
            throw new DomainException("Invoice already exists for order %s".formatted(orderId));
        }
        return Invoice.issue(orderId, customerId, payer, items);
    }

    public void assignPayment(final Invoice invoice, final Payment payment) {
        invoice.assignPaymentGatewayCode(payment.gatewayCode());
        switch (payment.status()) {
            case FAILED -> invoice.cancel("Payment failed");
            case REFUNDED -> invoice.cancel("Payment refunded");
            case PAID -> invoice.markAsPaid();
        }
    }

}
