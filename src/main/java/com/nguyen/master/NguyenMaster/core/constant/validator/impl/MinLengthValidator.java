package com.nguyen.master.NguyenMaster.core.constant.validator.impl;

import com.nguyen.master.NguyenMaster.core.constant.validator.anotaion.MinLength;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MinLengthValidator implements ConstraintValidator<MinLength, Object> {
    private long minLength;

    @Override
    public void initialize(MinLength constraintAnnotation) {
        this.minLength = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (o == null) {
            return true;
        }
        return o.toString().length() >= minLength;
    }
}
