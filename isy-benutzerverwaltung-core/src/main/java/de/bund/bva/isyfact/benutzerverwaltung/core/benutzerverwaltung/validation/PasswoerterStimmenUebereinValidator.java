package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation;

import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.PasswortZuruecksetzen;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PasswoerterStimmenUebereinValidator
    implements ConstraintValidator<PasswoerterStimmenUeberein, PasswortZuruecksetzen> {

    @Override
    public void initialize(PasswoerterStimmenUeberein constraintAnnotation) {
    }

    @Override
    public boolean isValid(PasswortZuruecksetzen value, ConstraintValidatorContext context) {
        return value == null || Objects
            .equals(value.getNeuesPasswort(), value.getNeuesPasswortBestaetigung());
    }
}
