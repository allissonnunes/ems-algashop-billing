package br.dev.allissonnunes.algashop.billing.infrastructure.listener;

import br.dev.allissonnunes.algashop.billing.domain.model.invoice.InvoiceCanceledEvent;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.InvoiceIssuedEvent;
import br.dev.allissonnunes.algashop.billing.domain.model.invoice.InvoicePaidEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InvoiceEventListener {

    @EventListener
    public void listen(final InvoiceIssuedEvent event) {

    }

    @EventListener
    public void listen(final InvoicePaidEvent event) {

    }

    @EventListener
    public void listen(final InvoiceCanceledEvent event) {

    }

}
