package com.nguyen.master.NguyenMaster.core.constant.validator.impl;

import com.nguyen.master.NguyenMaster.core.constant.validator.anotaion.Size;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.ObjectUtils;

public class SizeValidator implements ConstraintValidator<Size, Object> {

    private long size;

    @Override
    public void initialize(Size constraintAnnotation) {
        this.size = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (ObjectUtils.isEmpty(o) || StringUtils.isEmpty(o.toString().trim())) {
            return true;
        }
        return o.toString().length() == this.size;
    }
}
