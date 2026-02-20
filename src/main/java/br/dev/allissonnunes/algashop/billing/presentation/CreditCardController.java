package br.dev.allissonnunes.algashop.billing.presentation;

import br.dev.allissonnunes.algashop.billing.application.creditcard.management.CreditCardManagementService;
import br.dev.allissonnunes.algashop.billing.application.creditcard.management.TokenizedCreditCardInput;
import br.dev.allissonnunes.algashop.billing.application.creditcard.query.CreditCardOutput;
import br.dev.allissonnunes.algashop.billing.application.creditcard.query.CreditCardQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;

@RestController
@RequestMapping("/api/v1/customers/{customerId}/credit-cards")
@RequiredArgsConstructor
class CreditCardController {

    private final CreditCardManagementService creditCardManagementService;

    private final CreditCardQueryService creditCardQueryService;

    @PostMapping
    public ResponseEntity<CreditCardOutput> register(@PathVariable final UUID customerId,
                                                     @RequestBody final @Valid TokenizedCreditCardRequest request) {
        final UUID registeredCardId = creditCardManagementService
                .register(new TokenizedCreditCardInput(customerId, request.tokenizedCard()));

        final CreditCardOutput creditCardOutput = creditCardQueryService
                .findById(customerId, registeredCardId);

        final var location = fromCurrentRequestUri()
                .path("/{creditCardId}")
                .buildAndExpand(registeredCardId)
                .toUri();

        return ResponseEntity.created(location).body(creditCardOutput);
    }

    @GetMapping
    public ResponseEntity<List<CreditCardOutput>> findAllByCustomer(@PathVariable final UUID customerId) {
        return ResponseEntity.ok(creditCardQueryService.findByCustomer(customerId));
    }

    @GetMapping("/{creditCardId}")
    public ResponseEntity<CreditCardOutput> findOne(@PathVariable final UUID customerId,
                                                    @PathVariable final UUID creditCardId) {
        final CreditCardOutput creditCardOutput = creditCardQueryService.findById(customerId, creditCardId);
        return ResponseEntity.ok(creditCardOutput);
    }

    @DeleteMapping("/{creditCardId}")
    public ResponseEntity<Void> delete(@PathVariable final UUID customerId,
                                       @PathVariable final UUID creditCardId) {
        creditCardManagementService.delete(customerId, creditCardId);
        return ResponseEntity.noContent().build();
    }

}
