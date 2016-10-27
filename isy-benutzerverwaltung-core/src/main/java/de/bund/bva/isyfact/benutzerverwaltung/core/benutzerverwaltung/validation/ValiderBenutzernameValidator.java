package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValiderBenutzernameValidator implements ConstraintValidator<ValiderBenutzername, String> {

    @Override
    public void initialize(ValiderBenutzername constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isEmpty(value) || value.matches("[a-zA-Z0-9\\-_.]+");
    }
}
