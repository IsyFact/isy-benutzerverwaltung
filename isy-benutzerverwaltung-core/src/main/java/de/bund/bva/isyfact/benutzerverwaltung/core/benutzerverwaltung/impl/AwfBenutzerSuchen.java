package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.impl;

import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierrichtung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSuchkriterien;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.BenutzerDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Benutzer;
import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;

import javax.validation.Validator;
import java.util.List;

/**
 * Dieser Anwendungsfall holt persistente {@link Benutzer Benutzer-Entitaeten} aus der Datenbank.
 *
 * @author Capgemini, Jonas Zitz
 * @version $Id: AwfBenutzerSuchen.java 41878 2013-07-26 10:31:50Z jozitz $
 */
class AwfBenutzerSuchen extends AbstractAnwendungsfall {

    private final static IsyLogger LOG = IsyLoggerFactory.getLogger(AwfBenutzerSuchen.class);

    AwfBenutzerSuchen(BenutzerDao benutzerDao, Validator validator) {
        super(benutzerDao, validator);
    }

    /**
     * Liest einen Benutzer anhand des Benutzernamens aus.
     *
     * @param benutzername Benutzername
     * @return die Daten des Benutzers, falls vorhanden, oder {@code null}.
     * @throws BenutzerverwaltungBusinessException wenn der Benutzer nicht existiert.
     */
    public Benutzer leseBenutzer(String benutzername) throws BenutzerverwaltungBusinessException {
        return super.leseBenutzer(benutzername);
    }

    /**
     * Sucht Benutzer anhand von {@link BenutzerSuchkriterien Suchkriterien} und sortiert die Treffer
     * anschließend nach einem {@link BenutzerSortierattribut Sortierattribut}.
     *
     * @param suchkriterien enthält die Suchkriterien
     * @param sortierung    enthält das Sortierattribut und die Reihenfolge der Sortierung (aufsteigend,
     *                      absteigend)
     * @param paginierung   enthält den ersten Treffer und die maximale Größe der Trefferliste
     * @return eine sortierte Liste von Benutzern.
     * @throws BenutzerverwaltungValidationException wenn die Suchkriterien ungültig sind.
     */
    public List<Benutzer> sucheBenutzer(BenutzerSuchkriterien suchkriterien, Sortierung sortierung,
        Paginierung paginierung) throws BenutzerverwaltungValidationException {
        if (suchkriterien == null) {
            suchkriterien = new BenutzerSuchkriterien();
        }
        if (sortierung == null) {
            sortierung = new Sortierung(BenutzerSortierattribut.getStandard(), Sortierrichtung.getStandard());
        }

        LOG.debug("Validiere Suchparameter.");
        validiere(suchkriterien, sortierung, paginierung);

        LOG.debug("Hole gefilterte und sortiere Benutzer aus der Datenbank.");
        return benutzerDao.sucheMitBenutzerFilter(suchkriterien, sortierung, paginierung);
    }

    /**
     * Zählt Benutzer anhand von {@link BenutzerSuchkriterien Suchkriterien}.
     *
     * @param suchkriterien enthält die Suchkriterien
     * @return die Anzahl der Benutzer, die den Suchkriterien entsprechen.
     * @throws BenutzerverwaltungValidationException falls die Eingabedaten ungültig sind.
     */
    public long zaehleBenutzer(BenutzerSuchkriterien suchkriterien)
        throws BenutzerverwaltungValidationException {
        if (suchkriterien == null) {
            suchkriterien = new BenutzerSuchkriterien();
        }

        LOG.debug("Validiere Suchparameter.");
        validiere(suchkriterien);

        LOG.debug("Hole gefilterte und sortiere Benutzer aus der Datenbank.");
        return benutzerDao.zaehleMitKriterien(suchkriterien);
    }

}
