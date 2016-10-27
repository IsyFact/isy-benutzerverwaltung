package de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.impl;

import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierrichtung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungTechnicalRuntimeException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.FehlerSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.ValidierungSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.RolleSortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.RolleSuchkriterien;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.RollenDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Rolle;

import javax.validation.Validator;
import java.util.List;

class AwfRollenSuchen extends AbstractAnwendungsfall {

    private final RollenDao rollenDao;

    AwfRollenSuchen(RollenDao rollenDao, Validator validator) {
        super(validator);
        this.rollenDao = rollenDao;
    }

    /**
     * Liest eine Rolle anhand ihrer Rollen-ID aus.
     *
     * @param rolleId ID der Rolle
     * @return die Daten der Rolle, falls vorhanden, oder {@code null}.
     * @throws BenutzerverwaltungBusinessException falls die Rolle nicht existiert.
     */
    Rolle leseRolle(String rolleId) throws BenutzerverwaltungBusinessException {
        if (rolleId == null) {
            throw new BenutzerverwaltungTechnicalRuntimeException(FehlerSchluessel.MSG_EINGABEDATEN_FEHLEN);
        }

        Rolle rolle = rollenDao.sucheMitRollenId(rolleId);
        if (rolle == null) {
            throw new BenutzerverwaltungBusinessException(ValidierungSchluessel.MSG_ROLLE_NICHT_VORHANDEN,
                rolleId);
        }
        return rolle;
    }

    /**
     * Sucht Rollen anhand von {@link RolleSuchkriterien Suchkriterien} und sortiert die Treffer
     * anschließend nach einem {@link RolleSortierattribut Sortierattribut}.
     *
     * @param filter     enthält die Suchkriterien
     * @param sortierung enthält das Sortierattribut und die Reihenfolge der Sortierung (aufsteigend,
     *                   absteigend)
     * @return eine sortierte Liste von Rollen.
     * @throws BenutzerverwaltungValidationException falls die Eingabedaten ungültig sind.
     */
    List<Rolle> sucheRollen(RolleSuchkriterien filter, Sortierung sortierung, Paginierung paginierung)
        throws BenutzerverwaltungValidationException {
        if (filter == null) {
            filter = new RolleSuchkriterien();
        }
        if (sortierung == null) {
            sortierung = new Sortierung(RolleSortierattribut.getStandard(), Sortierrichtung.getStandard());
        }

        validiere(filter, sortierung, paginierung);

        return rollenDao.sucheMitFilter(filter, sortierung, paginierung);
    }

    /**
     * Zählt Rollen anhand von Suchkriterien.
     *
     * @param filter enthält die Suchkriterien
     * @return die Anzahl der Benutzer, die den Suchkriterien entsprechen.
     * @throws BenutzerverwaltungValidationException falls die Eingabedaten ungültig sind.
     */
    long zaehleRollen(RolleSuchkriterien filter) throws BenutzerverwaltungValidationException {
        if (filter == null) {
            filter = new RolleSuchkriterien();
        }

        validiere(filter);
        return rollenDao.zaehleMitKriterien(filter);
    }

}
