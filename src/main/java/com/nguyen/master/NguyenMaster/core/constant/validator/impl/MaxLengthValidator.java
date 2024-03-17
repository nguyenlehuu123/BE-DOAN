package com.nguyen.master.NguyenMaster.core.constant.validator.impl;


import com.nguyen.master.NguyenMaster.core.constant.validator.anotaion.MaxLength;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MaxLengthValidator implements ConstraintValidator<MaxLength, Object> {
    private long maxLength;


    @Override
    public void initialize(MaxLength constraintAnnotation) {
        this.maxLength = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (o == null) {
            return true;
        }
        return o.toString().length() <= this.maxLength;
    }
}
