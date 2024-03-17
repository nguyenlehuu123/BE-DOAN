package com.nguyen.master.NguyenMaster.core.constant.validator.anotaion;


import com.nguyen.master.NguyenMaster.core.constant.validator.impl.LengthValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {LengthValidator.class})
public @interface Length {
    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String message() default "The length of the input value is not within the allowed range";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
