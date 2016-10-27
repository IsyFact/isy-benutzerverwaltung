package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation;

import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.BenutzerDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BekannterBenutzernameValidator implements ConstraintValidator<BekannterBenutzername, String> {

    @Autowired
    private BenutzerDao benutzerDao;

    @Override
    public void initialize(BekannterBenutzername constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || benutzerDao.sucheMitBenutzername(value) != null;
    }
}
