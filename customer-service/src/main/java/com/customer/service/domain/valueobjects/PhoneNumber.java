package com.customer.service.domain.valueobjects;

import lombok.Value;
import java.util.regex.Pattern;

@Value
public class PhoneNumber {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[1-9]\\d{1,14}$");
    
    String value;
    
    public PhoneNumber(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be blank");
        }
        String trimmed = value.trim();
        if (!PHONE_PATTERN.matcher(trimmed).matches()) {
            throw new IllegalArgumentException("Phone number must be in E.164 format: " + value);
        }
        this.value = trimmed;
    }
}

