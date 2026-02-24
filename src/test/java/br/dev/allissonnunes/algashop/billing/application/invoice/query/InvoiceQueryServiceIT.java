package br.dev.allissonnunes.algashop.billing.application.invoice.query;

import br.dev.allissonnunes.algashop.billing.domain.model.invoice.Invoice;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.InvoiceRepository;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.InvoiceTestDataBuilder;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.PaymentMethod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class InvoiceQueryServiceIT {

    @Autowired
    private InvoiceQueryService invoiceQueryService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Test
    public void shouldFindByOrderId() {
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().build();
        invoice.changePaymentSettings(PaymentMethod.GATEWAY_BALANCE, null);
        invoiceRepository.saveAndFlush(invoice);
        InvoiceOutput invoiceOutput = invoiceQueryService.findByOrderId(invoice.getOrderId());

        assertThat(invoiceOutput.id()).isEqualTo(invoice.getId());
    }

}