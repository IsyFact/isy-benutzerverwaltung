package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation;

import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.RollenDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ValideRollenValidator implements ConstraintValidator<ValideRollen, List<RolleDaten>> {

    @Autowired
    private RollenDao rollenDao;

    @Override
    public void initialize(ValideRollen constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<RolleDaten> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        for (RolleDaten rolle : value) {
            if (rollenDao.sucheMitRollenId(rolle.getRollenId()) == null) {
                return false;
            }
        }
        return true;
    }
}
