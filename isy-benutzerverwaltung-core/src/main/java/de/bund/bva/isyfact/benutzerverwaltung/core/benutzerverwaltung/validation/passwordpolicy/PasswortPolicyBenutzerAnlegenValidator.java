package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation.passwordpolicy;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.passwortpolicy.PasswortPolicy;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.passwortpolicy.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;

public class PasswortPolicyBenutzerAnlegenValidator implements ConstraintValidator<PasswortPolicyUeberpruefen, String> {

    @Autowired
    private PasswortPolicy passwortPolicy;

    @Override
    public void initialize(PasswortPolicyUeberpruefen constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        ValidationResult result = passwortPolicy.validate(value, null);

        if (result.isValid()) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(result.getMessage()).addConstraintViolation();
            return false;
        }
    }
}
