package dev.lynxie.webapi.master.validation;

import dev.lynxie.webapi.master.validation.validator.FieldMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FieldMatchValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldMatch {
    String message() default "Password and repeatedPassword should be the same";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String first();

    String second();
}
