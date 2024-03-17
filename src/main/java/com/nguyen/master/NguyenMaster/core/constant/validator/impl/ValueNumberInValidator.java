package com.nguyen.master.NguyenMaster.core.constant.validator.impl;

import com.nguyen.master.NguyenMaster.core.constant.validator.anotaion.ValueNumberIn;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ValueNumberInValidator implements ConstraintValidator<ValueNumberIn, Integer> {
    private List<Integer> values;

    @Override
    public void initialize(ValueNumberIn constraintAnnotation) {
        values = Arrays.stream(constraintAnnotation.value()).boxed().collect(Collectors.toList());
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value == null || values.contains(value);
    }
}
