package de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.validation;

import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.RollenDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UnbekannteRollenIdValidator implements ConstraintValidator<UnbekannteRollenId, String> {

    @Autowired
    private RollenDao rollenDao;

    @Override
    public void initialize(UnbekannteRollenId constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || rollenDao.sucheMitRollenId(value) == null;
    }
}
