package br.dev.allissonnunes.algashop.billing.presentation;

import br.dev.allissonnunes.algashop.billing.application.invoice.management.GenerateInvoiceInput;
import br.dev.allissonnunes.algashop.billing.application.invoice.management.InvoiceManagementApplicationService;
import br.dev.allissonnunes.algashop.billing.application.invoice.query.InvoiceOutput;
import br.dev.allissonnunes.algashop.billing.application.invoice.query.InvoiceQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;

@RestController
@RequestMapping("/api/v1/orders/{orderId}/invoice")
@RequiredArgsConstructor
@Slf4j
class InvoiceController {

    private final InvoiceManagementApplicationService invoiceManagementApplicationService;

    private final InvoiceQueryService invoiceQueryService;

    @GetMapping
    public ResponseEntity<InvoiceOutput> findByOrderId(@PathVariable final String orderId) {
        return ResponseEntity.ok(invoiceQueryService.findByOrderId(orderId));
    }

    @PostMapping
    public ResponseEntity<InvoiceOutput> generate(@PathVariable final String orderId,
                                                  @RequestBody final @Valid GenerateInvoiceRequest request) {
        final GenerateInvoiceInput input = GenerateInvoiceInput.builder()
                .orderId(orderId)
                .customerId(request.customerId())
                .paymentSettings(request.paymentSettings())
                .payer(request.payer())
                .items(request.items())
                .build();

        final UUID generatedInvoiceId = invoiceManagementApplicationService.generate(input);

        try {
            invoiceManagementApplicationService.processPayment(generatedInvoiceId);
        } catch (final Exception e) {
            log.error("Error when processing payment for invoice ID: {}", generatedInvoiceId, e);
        }

        final InvoiceOutput invoiceOutput = invoiceQueryService.findByOrderId(orderId);

        final var location = fromCurrentRequestUri().build().toUri();

        return ResponseEntity.created(location).body(invoiceOutput);
    }

}
