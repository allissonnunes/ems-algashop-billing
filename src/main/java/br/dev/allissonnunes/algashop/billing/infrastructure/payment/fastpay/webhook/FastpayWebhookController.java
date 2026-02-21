package br.dev.allissonnunes.algashop.billing.infrastructure.payment.fastpay.webhook;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/webhooks/fastpay")
@RequiredArgsConstructor
class FastpayWebhookController {

    private final FastpayWebhookHandler handler;

    @PostMapping
    public ResponseEntity<Void> receive(@RequestBody final @Valid FastpayPaymentWebhookEvent event) {
        handler.handle(event);
        return ResponseEntity.ok().build();
    }

}
