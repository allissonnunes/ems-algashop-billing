package br.dev.allissonnunes.algashop.billing.infrastructure.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

public interface SecurityAnnotations {

    @Target({ ElementType.METHOD, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    @PreAuthorize("hasAuthority('SCOPE_invoices:read')")
    @interface CanReadInvoices {

    }

    @Target({ ElementType.METHOD, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    @PreAuthorize("hasAuthority('SCOPE_invoices:write')")
    @interface CanWriteInvoices {

    }

    @Target({ ElementType.METHOD, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    @PreAuthorize("hasAuthority('SCOPE_credit-cards:read')")
    @interface CanReadCreditCards {

    }

    @Target({ ElementType.METHOD, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    @PreAuthorize("hasAuthority('SCOPE_credit-cards:write')")
    @interface CanWriteCreditCards {

    }

}
