package com.company.wordapp.validator.impl;

import com.company.wordapp.validator.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApiKeyValidator implements Validator<String> {
    
    @Value("${wordClient.apiKey}")
    private String apiKey;

    public boolean isValid(String data) {
        if (data == null) {
            return false;
        }
        return data.equals(apiKey);
    }
}
