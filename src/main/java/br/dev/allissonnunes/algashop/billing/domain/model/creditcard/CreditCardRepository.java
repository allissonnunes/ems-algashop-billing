package br.dev.allissonnunes.algashop.billing.domain.model.creditcard;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface CreditCardRepository extends JpaRepository<CreditCard, UUID> {

    boolean existsByIdAndCustomerId(UUID id, UUID customerId);

    Optional<CreditCard> findCreditCardByCustomerIdAndId(UUID customerId, UUID id);

    Collection<CreditCard> findAllByCustomerId(UUID customerId);

}