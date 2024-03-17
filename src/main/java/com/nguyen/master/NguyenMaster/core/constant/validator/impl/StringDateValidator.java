package com.nguyen.master.NguyenMaster.core.constant.validator.impl;

import com.nguyen.master.NguyenMaster.core.constant.validator.anotaion.StringDate;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringDateValidator implements ConstraintValidator<StringDate, String> {
    private String format;

    @Override
    public void initialize(StringDate constraintAnnotation) {
        this.format = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.format);

        try {
            Date finalDate = simpleDateFormat.parse(s);
            String finalFormat = simpleDateFormat.format(finalDate);
            return finalFormat.equals(s);
        } catch (ParseException e) {
            return false;
        }
    }
}
