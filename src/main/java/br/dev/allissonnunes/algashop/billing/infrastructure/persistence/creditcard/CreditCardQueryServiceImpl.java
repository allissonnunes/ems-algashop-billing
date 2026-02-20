package br.dev.allissonnunes.algashop.billing.infrastructure.persistence.creditcard;

import br.dev.allissonnunes.algashop.billing.application.creditcard.query.CreditCardOutput;
import br.dev.allissonnunes.algashop.billing.application.creditcard.query.CreditCardQueryService;
import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.CreditCard;
import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.CreditCardNotFoundException;
import br.dev.allissonnunes.algashop.billing.domain.model.creditcard.CreditCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class CreditCardQueryServiceImpl implements CreditCardQueryService {

    private final CreditCardRepository creditCardRepository;

    @Override
    public CreditCardOutput findById(final UUID customerId, final UUID creditCardId) {
        return creditCardRepository.findCreditCardByCustomerIdAndId(customerId, creditCardId)
                .map(this::convert)
                .orElseThrow(() -> new CreditCardNotFoundException(creditCardId));
    }

    @Override
    public List<CreditCardOutput> findByCustomer(UUID customerId) {
        return creditCardRepository.findAllByCustomerId(customerId)
                .stream()
                .map(this::convert)
                .toList();
    }

    private CreditCardOutput convert(final CreditCard creditCard) {
        return CreditCardOutput.builder()
                .id(creditCard.getId())
                .lastNumbers(creditCard.getLastNumbers())
                .expirationMonth(creditCard.getExpirationMonth())
                .expirationYear(creditCard.getExpirationYear())
                .brand(creditCard.getBrand())
                .build();
    }

}
