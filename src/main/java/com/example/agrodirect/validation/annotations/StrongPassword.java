package com.example.agrodirect.validation.annotations;

import com.example.agrodirect.validation.validators.StrongPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { StrongPasswordValidator.class })
public @interface StrongPassword {

    String message() default "Password must be at least 8 characters long and contain a digit, uppercase letter, lowercase letter, and special symbol.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
