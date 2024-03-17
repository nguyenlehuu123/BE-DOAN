package com.nguyen.master.NguyenMaster.core.constant.validator.anotaion;

import com.nguyen.master.NguyenMaster.core.constant.validator.impl.MaxLengthValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {MaxLengthValidator.class})
public @interface MaxLength {
    long value();

    String message() default "The input value exceeds the allowable length";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
