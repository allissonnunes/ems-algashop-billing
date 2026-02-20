package br.dev.allissonnunes.algashop.billing.application.creditcard.management;

import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreditCardManagementService {

    private final CreditCardRepository creditCardRepository;

    private final CreditCardProviderService creditCardProviderService;

    @Transactional
    public UUID register(final TokenizedCreditCardInput input) {
        final LimitedCreditCard limitedCreditCard = creditCardProviderService.register(input.customerId(), input.tokenizedCard());

        final CreditCard creditCard = CreditCard.brandNew(
                input.customerId(),
                limitedCreditCard.lastNumbers(),
                limitedCreditCard.brand(),
                limitedCreditCard.expirationMonth(),
                limitedCreditCard.expirationYear(),
                limitedCreditCard.gatewayCode()
        );

        creditCardRepository.saveAndFlush(creditCard);

        return creditCard.getId();
    }

    @Transactional
    public void delete(final UUID customerId, final UUID creditCardId) {
        final CreditCard creditCard = creditCardRepository.findCreditCardByCustomerIdAndId(customerId, creditCardId)
                .orElseThrow(() -> new CreditCardNotFoundException(creditCardId));

        creditCardRepository.delete(creditCard);
        creditCardProviderService.delete(creditCardId.toString());
    }

}
