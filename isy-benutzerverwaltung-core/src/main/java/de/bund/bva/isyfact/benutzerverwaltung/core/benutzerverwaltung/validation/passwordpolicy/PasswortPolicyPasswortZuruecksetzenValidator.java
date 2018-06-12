package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation.passwordpolicy;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.PasswortZuruecksetzen;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.passwortpolicy.PasswortPolicy;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.passwortpolicy.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;

public class PasswortPolicyPasswortZuruecksetzenValidator implements ConstraintValidator<PasswortPolicyUeberpruefen, PasswortZuruecksetzen> {

    @Autowired
    private PasswortPolicy passwortPolicy;

    @Override
    public void initialize(PasswortPolicyUeberpruefen constraintAnnotation) {

    }

    @Override
    public boolean isValid(PasswortZuruecksetzen value, ConstraintValidatorContext context) {

        ValidationResult result = passwortPolicy.validate(value.getNeuesPasswort(), value.getBenutzername());

        if (result.isValid()) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(result.getMessage()).addConstraintViolation();
            return false;
        }
    }
}

