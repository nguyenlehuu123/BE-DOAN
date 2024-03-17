package com.nguyen.master.NguyenMaster.core.constant.validator.anotaion;

import com.nguyen.master.NguyenMaster.core.constant.validator.impl.StringDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {StringDateValidator.class})
public @interface StringDate {
    String value();

    String message() default "Enter date in the same format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}