package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.impl;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungTechnicalRuntimeException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.FehlerSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.BenutzerDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Benutzer;

import javax.validation.Validator;
import java.util.Date;

/**
 * Implementiert Anwendungsfälle zum An- und Abmelden von Benutzern. Hier wird nicht die eigentliche An- und
 * Abmeldung durchgeführt, sondern der Abschluss der Vorgänge im Datenmodell vermerkt.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
class AwfBenutzerAnAbmelden extends AbstractAnwendungsfall {

    AwfBenutzerAnAbmelden(BenutzerDao benutzerDao, Validator validator) {
        super(benutzerDao, validator);
    }

    Benutzer speichereErfolgreicheAnmeldung(String benutzername) throws BenutzerverwaltungBusinessException {
        if (benutzername == null) {
            throw new BenutzerverwaltungTechnicalRuntimeException(FehlerSchluessel.MSG_EINGABEDATEN_FEHLEN);
        }

        Benutzer benutzer = leseBenutzer(benutzername);
        benutzer.setFehlanmeldeVersuche(0);
        benutzer.setLetzteAnmeldung(new Date());
        benutzerDao.speichere(benutzer);
        return benutzer;
    }

    Benutzer speichereFehlgeschlageneAnmeldung(String benutzername)
        throws BenutzerverwaltungBusinessException {
        if (benutzername == null) {
            throw new BenutzerverwaltungTechnicalRuntimeException(FehlerSchluessel.MSG_EINGABEDATEN_FEHLEN);
        }

        Benutzer benutzer = leseBenutzer(benutzername);
        benutzer.setFehlanmeldeVersuche(benutzer.getFehlanmeldeVersuche() + 1);
        benutzerDao.speichere(benutzer);
        return benutzer;
    }

    Benutzer speichereAbmeldung(String benutzername) throws BenutzerverwaltungBusinessException {
        if (benutzername == null) {
            throw new BenutzerverwaltungTechnicalRuntimeException(FehlerSchluessel.MSG_EINGABEDATEN_FEHLEN);
        }

        Benutzer benutzer = leseBenutzer(benutzername);
        benutzer.setLetzteAbmeldung(new Date());
        benutzerDao.speichere(benutzer);
        return benutzer;
    }

}
