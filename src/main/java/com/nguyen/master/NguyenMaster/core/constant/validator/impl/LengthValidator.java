package com.nguyen.master.NguyenMaster.core.constant.validator.impl;

import com.nguyen.master.NguyenMaster.core.constant.validator.anotaion.Length;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LengthValidator implements ConstraintValidator<Length, String> {

    private Integer min;
    private Integer max;

    @Override
    public void initialize(Length constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || StringUtils.isEmpty(value.trim())) {
            return true;
        } else {
            return value.length() >= this.min && value.length() <= this.max;
        }
    }
}
