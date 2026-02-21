package br.dev.allissonnunes.algashop.billing.infrastructure.payment.fastpay.webhook;

import br.dev.allissonnunes.algashop.billing.application.invoice.management.InvoiceManagementApplicationService;
import br.dev.allissonnunes.algashop.billing.infrastructure.payment.fastpay.FastpayEnumConverter;
import br.dev.allissonnunes.algashop.billing.infrastructure.payment.fastpay.FastpayPaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
class FastpayWebhookHandler {

    private final InvoiceManagementApplicationService invoiceManagementApplicationService;

    public void handle(final FastpayPaymentWebhookEvent event) {
        log.info("Processing Fastpay webhook event: {}", event);
        invoiceManagementApplicationService.updateInvoiceStatus(
                UUID.fromString(event.referenceCode()),
                FastpayEnumConverter.convert(FastpayPaymentStatus.valueOf(event.status()))
        );
    }

}
