package com.company.wordapp.validator;

import org.springframework.lang.Nullable;

public interface Validator<T> {
    boolean isValid(@Nullable T model);
}