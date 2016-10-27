package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation;

import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.PasswortAendern;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.BenutzerDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Benutzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswortKorrektValidator implements ConstraintValidator<PasswortKorrekt, PasswortAendern> {

    @Autowired
    private BenutzerDao benutzerDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void initialize(PasswortKorrekt constraintAnnotation) {
    }

    @Override
    public boolean isValid(PasswortAendern value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        Benutzer benutzer = benutzerDao.sucheMitBenutzername(value.getBenutzername());
        return passwordEncoder.matches(value.getAltesPasswort(), benutzer.getPasswort());
    }
}
