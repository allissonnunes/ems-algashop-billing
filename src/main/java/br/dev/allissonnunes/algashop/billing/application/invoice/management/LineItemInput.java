package br.dev.allissonnunes.algashop.billing.application.invoice.management;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record LineItemInput(String name, BigDecimal amount) {

}
