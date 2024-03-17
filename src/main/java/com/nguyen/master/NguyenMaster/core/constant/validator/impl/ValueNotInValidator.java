package com.nguyen.master.NguyenMaster.core.constant.validator.impl;

import com.nguyen.master.NguyenMaster.core.constant.validator.anotaion.ValueNotIn;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class ValueNotInValidator implements ConstraintValidator<ValueNotIn, String> {
    private List<String> valueList;

    @Override
    public void initialize(ValueNotIn constraintAnnotation) {
        valueList = new ArrayList<>();
        for (String val : constraintAnnotation.value()) {
            valueList.add(val.toUpperCase());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return !valueList.contains(value);
    }
}
