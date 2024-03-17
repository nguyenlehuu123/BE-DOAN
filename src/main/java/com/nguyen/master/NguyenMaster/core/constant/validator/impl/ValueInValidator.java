package com.nguyen.master.NguyenMaster.core.constant.validator.impl;

import com.nguyen.master.NguyenMaster.core.constant.validator.anotaion.ValueIn;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValueInValidator implements ConstraintValidator<ValueIn, String> {

    private List<String> values;

    @Override
    public void initialize(ValueIn constraintAnnotation) {
        values = new ArrayList<>();
        Collections.addAll(values, constraintAnnotation.value());
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return values.contains(s);
    }
}
