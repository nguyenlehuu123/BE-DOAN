package com.nguyen.master.NguyenMaster.core.constant.validator.impl;

import com.nguyen.master.NguyenMaster.core.constant.validator.anotaion.Required;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.ObjectUtils;

public class RequiredValidator implements ConstraintValidator<Required, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (o instanceof String) {
            return !StringUtils.isEmpty(o.toString().trim());
        }
        return !ObjectUtils.isEmpty(o);
    }
}
