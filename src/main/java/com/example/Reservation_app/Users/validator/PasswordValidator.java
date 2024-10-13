package com.example.Reservation_app.Users.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return validate(password, context);
    }

    private boolean validate(String password, ConstraintValidatorContext context) {
        return lengthCheck(password, context) && upperCaseCheck(password, context) && numberAndSpecialsCheck(password, context);
    }

    private boolean lengthCheck(String password, ConstraintValidatorContext context) {
        // Check if the password is at least 8 characters long
        if (password == null || password.length() < 8) {
            buildContext("Password must be at least 8 characters long", context);
            return false;
        }
        return true;
    }

    private boolean upperCaseCheck(String password, ConstraintValidatorContext context) {
        // Check if the password contains at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            buildContext("Password must contain at least one uppercase letter", context);
            return false;
        }
        return true;
    }

    private boolean numberAndSpecialsCheck(String password, ConstraintValidatorContext context) {
        // Check if the password contains at least one digit and one special character
        if (!password.matches(".*\\d.*")) {
            buildContext("Password must contain at least one number", context);
            return false;
        }
        if (!password.matches(".*[!@#$%^&*()_+\\-=[\\]{};':\"\\\\|,.<>/?].*")) {
            buildContext("Password must contain at least one special character", context);
            return false;
        }
        return true;
    }

    private void buildContext(String message, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}

