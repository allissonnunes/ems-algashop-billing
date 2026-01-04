package com.github.allisson95.algashop.billing.domain.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

public final class Strings {

    private Strings() {
        throw new IllegalStateException("Utility class");
    }

    public static String requireNonBlank(final String value) {
        return requireNonBlank(value, null);
    }

    public static String requireNonBlank(final String value, final String message) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static String requireValidEmail(final String value) {
        return requireValidEmail(value, null);
    }

    public static String requireValidEmail(final String value, final String message) {
        requireNonBlank(value, message);
        if (!EmailValidator.getInstance().isValid(value)) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

}
