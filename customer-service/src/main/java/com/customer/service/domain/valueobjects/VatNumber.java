package com.customer.service.domain.valueobjects;

import lombok.Value;
import java.util.regex.Pattern;

@Value
public class VatNumber {
    private static final Pattern VAT_PATTERN = Pattern.compile("^[A-Z]{2}[A-Z0-9]{2,12}$");
    
    String value;
    
    public VatNumber(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("VAT number cannot be blank");
        }
        String trimmed = value.trim().toUpperCase();
        if (!VAT_PATTERN.matcher(trimmed).matches()) {
            throw new IllegalArgumentException("VAT number must be valid (e.g., FR12345678901): " + value);
        }
        this.value = trimmed;
    }
}

