CREATE TABLE public.credit_card
(
    id               UUID NOT NULL,
    created_at       TIMESTAMP WITHOUT TIME ZONE,
    customer_id      UUID,
    last_numbers     VARCHAR(255),
    brand            VARCHAR(255),
    expiration_month INTEGER,
    expiration_year  INTEGER,
    gateway_code     VARCHAR(255),
    CONSTRAINT pk_credit_card PRIMARY KEY (id)
);

CREATE INDEX idx_credit_card_customer_id ON public.credit_card (customer_id);

CREATE TABLE public.payment_settings
(
    id             UUID NOT NULL,
    credit_card_id UUID,
    gateway_code   VARCHAR(255),
    method         VARCHAR(255),
    CONSTRAINT pk_payment_settings PRIMARY KEY (id)
);

CREATE INDEX idx_payment_settings_credit_card_id ON public.payment_settings (credit_card_id);
ALTER TABLE payment_settings
    ADD CONSTRAINT fk_payment_settings_on_credit_card FOREIGN KEY (credit_card_id) REFERENCES credit_card (id);

CREATE TABLE public.invoice
(
    id                         UUID NOT NULL,
    order_id                   VARCHAR(255),
    customer_id                UUID,
    issued_at                  TIMESTAMP WITHOUT TIME ZONE,
    paid_at                    TIMESTAMP WITHOUT TIME ZONE,
    canceled_at                TIMESTAMP WITHOUT TIME ZONE,
    expires_at                 TIMESTAMP WITHOUT TIME ZONE,
    total_amount               DECIMAL,
    status                     VARCHAR(255),
    payment_settings_id        UUID,
    cancellation_reason        VARCHAR(255),
    payer_full_name            VARCHAR(255),
    payer_email                VARCHAR(255),
    payer_document             VARCHAR(255),
    payer_phone                VARCHAR(255),
    payer_address_street       VARCHAR(255),
    payer_address_number       VARCHAR(255),
    payer_address_complement   VARCHAR(255),
    payer_address_neighborhood VARCHAR(255),
    payer_address_city         VARCHAR(255),
    payer_address_state        VARCHAR(255),
    payer_address_zip_code     VARCHAR(255),
    CONSTRAINT pk_invoice PRIMARY KEY (id)
);

CREATE INDEX idx_invoice_customer_id ON public.invoice (customer_id);
CREATE INDEX idx_invoice_order_id ON public.invoice (order_id);
CREATE INDEX idx_invoice_payment_settings_id ON public.invoice (payment_settings_id);

ALTER TABLE invoice
    ADD CONSTRAINT fk_invoice_on_payment_settings FOREIGN KEY (payment_settings_id) REFERENCES payment_settings (id);

CREATE TABLE public.invoice_line_item
(
    invoice_id UUID NOT NULL,
    number     INTEGER,
    name       VARCHAR(255),
    amount     DECIMAL
);

CREATE INDEX idx_invoice_line_item_invoice_id ON public.invoice_line_item (invoice_id);
ALTER TABLE invoice_line_item
    ADD CONSTRAINT fk_invoice_line_item_on_invoice FOREIGN KEY (invoice_id) REFERENCES invoice (id);