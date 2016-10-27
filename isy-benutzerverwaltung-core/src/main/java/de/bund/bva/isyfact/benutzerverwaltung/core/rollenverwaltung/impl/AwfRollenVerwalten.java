package de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.impl;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungTechnicalRuntimeException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.FehlerSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.ValidierungSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.daten.RolleAendern;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.daten.RolleAnlegen;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.RollenDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Rolle;
import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;

import javax.validation.Validator;

/**
 * Diese Klasse bietet Funktionalität zum Erstellen, Bearbeiten und Löschen
 * einer Rolle.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
class AwfRollenVerwalten extends AbstractAnwendungsfall {
    private final static IsyLogger LOG = IsyLoggerFactory.getLogger(AwfRollenVerwalten.class);

    private final RollenDao rollenDao;

    AwfRollenVerwalten(RollenDao rollenDao, Validator validator) {
        super(validator);
        this.rollenDao = rollenDao;
    }

    /**
     * Legt eine neue Rolle an.
     *
     * @param rolleAnlegen Daten zum Anlegen der Rolle
     * @return die neue Rolle.
     * @throws BenutzerverwaltungValidationException falls die Eingabedaten ungültig sind.
     */
    Rolle legeRolleAn(RolleAnlegen rolleAnlegen) throws BenutzerverwaltungValidationException {
        if (rolleAnlegen == null) {
            throw new BenutzerverwaltungTechnicalRuntimeException(FehlerSchluessel.MSG_EINGABEDATEN_FEHLEN);
        }

        validiere(rolleAnlegen);

        Rolle rolle = new Rolle();
        rolle.setId(rolleAnlegen.getRollenId());
        rolle.setName(rolleAnlegen.getRollenName());

        rollenDao.speichere(rolle);
        return rolle;
    }

    /**
     * Ändert die Daten einer Rolle durch einen Administrator.
     *
     * @param rolleAendern Daten zur Änderung der Rolle
     * @return die aktualisierte Rolle.
     * @throws BenutzerverwaltungValidationException falls die Eingabedaten ungültig sind.
     */
    Rolle aendereRolle(RolleAendern rolleAendern) throws BenutzerverwaltungValidationException {
        if (rolleAendern == null) {
            throw new BenutzerverwaltungTechnicalRuntimeException(FehlerSchluessel.MSG_EINGABEDATEN_FEHLEN);
        }

        validiere(rolleAendern);

        Rolle rolle = rollenDao.sucheMitRollenId(rolleAendern.getAlteRollenId());
        if (rolleAendern.getNeueRollenId() != null) {
            rolle.setId(rolleAendern.getNeueRollenId());
        }
        rolle.setName(rolleAendern.getRollenName());

        rollenDao.speichere(rolle);
        return rolle;
    }

    /**
     * Löscht eine Rolle anhand ihrer ID.
     *
     * @param rolleId ID der Rolle
     * @throws BenutzerverwaltungValidationException falls die Eingabedaten ungültig sind.
     */
    void loescheRolle(String rolleId) throws BenutzerverwaltungBusinessException {
        if (rolleId == null) {
            throw new BenutzerverwaltungTechnicalRuntimeException(FehlerSchluessel.MSG_EINGABEDATEN_FEHLEN);
        }

        Rolle rolle = rollenDao.sucheMitRollenId(rolleId);
        if (rolle == null) {
            throw new BenutzerverwaltungBusinessException(ValidierungSchluessel.MSG_ROLLE_NICHT_VORHANDEN,
                rolleId);
        }
        rollenDao.loesche(rolle);
        LOG.debugFachdaten("Die Rolle '{}' wurde gelöscht.", rolle.getId());

    }

}
