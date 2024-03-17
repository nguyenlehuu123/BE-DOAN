package com.nguyen.master.NguyenMaster.core.constant.validator.anotaion;

import com.nguyen.master.NguyenMaster.core.constant.validator.impl.RequiredValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {RequiredValidator.class})
public @interface Required {
    String message() default "The value of this attribute is required";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
