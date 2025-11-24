package com.customer.service.domain.valueobjects;

import lombok.Getter;
import java.util.regex.Pattern;

@Getter
public final class VatNumber {
    private static final Pattern VAT_PATTERN = Pattern.compile("^[A-Z]{2}[A-Z0-9]{2,12}$");
    
    private final String value;
    
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VatNumber vatNumber = (VatNumber) o;
        return value != null ? value.equals(vatNumber.value) : vatNumber.value == null;
    }
    
    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return value;
    }
}

