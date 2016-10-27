package de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValideRollenIdValidator implements ConstraintValidator<ValideRollenId, String> {

    @Override
    public void initialize(ValideRollenId constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isEmpty(value) || value.matches("[a-zA-Z0-9\\-_.]+");
    }
}
