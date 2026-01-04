package com.github.allisson95.algashop.billing.domain.model.creditcard;

import java.util.UUID;

public final class CreditCardTestDataBuilder {

    private UUID customerId = UUID.randomUUID();

    private String lastNumbers = "1234";

    private String brand = "Visa";

    private Integer expirationMonth = 12;

    private Integer expirationYear = 2025;

    private String gatewayCreditCardCode = "123456";

    private CreditCardTestDataBuilder() {
    }

    public static CreditCardTestDataBuilder aCreditCard() {
        return new CreditCardTestDataBuilder();
    }

    public CreditCard build() {
        return CreditCard.brandNew(customerId, lastNumbers, brand, expirationMonth, expirationYear, gatewayCreditCardCode);
    }

    public CreditCardTestDataBuilder customerId(final UUID customerId) {
        this.customerId = customerId;
        return this;
    }

    public CreditCardTestDataBuilder lastNumbers(final String lastNumbers) {
        this.lastNumbers = lastNumbers;
        return this;
    }

    public CreditCardTestDataBuilder brand(final String brand) {
        this.brand = brand;
        return this;
    }

    public CreditCardTestDataBuilder expirationMonth(final Integer expirationMonth) {
        this.expirationMonth = expirationMonth;
        return this;
    }

    public CreditCardTestDataBuilder expirationYear(final Integer expirationYear) {
        this.expirationYear = expirationYear;
        return this;
    }

    public CreditCardTestDataBuilder gatewayCreditCardCode(final String gatewayCreditCardCode) {
        this.gatewayCreditCardCode = gatewayCreditCardCode;
        return this;
    }

}
