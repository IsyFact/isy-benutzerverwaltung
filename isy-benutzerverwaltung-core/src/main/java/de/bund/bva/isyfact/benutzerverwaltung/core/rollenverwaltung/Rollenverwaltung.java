package de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung;

import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Suchergebnis;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.daten.RolleAendern;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.daten.RolleAnlegen;

/**
 * Interface für die Komponente Rollenverwaltung. Diese Komponente bietet die Möglichkeit zur Verwaltung
 * (Suchen, Erstellen, Bearbeiten, Löschen) von {@link RolleDaten Rollen}.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public interface Rollenverwaltung {

    /**
     * Liest eine Rolle anhand ihrer Rollen-ID aus.
     *
     * @param rolleId ID der Rolle
     * @return die Daten der Rolle, falls vorhanden, oder {@code null}.
     * @throws BenutzerverwaltungBusinessException falls die Rolle nicht existiert.
     */
    RolleDaten leseRolle(String rolleId) throws BenutzerverwaltungBusinessException;

    /**
     * Sucht Rollen anhand von Suchkriterien und erzeugt eine Trefferliste anhand der übergebenen Sortierung
     * und Paginierung.
     *
     * @param filter      enthält die Suchkriterien
     * @param sortierung  enthält das Sortierattribut und die Reihenfolge der Sortierung (aufsteigend,
     *                    absteigend)
     * @param paginierung enthält den ersten Treffer und die maximale Größe der Trefferliste
     * @return eine sortierte Liste von Rollen.
     * @throws BenutzerverwaltungValidationException falls die Eingabedaten ungültig sind.
     */
    Suchergebnis<RolleDaten> sucheRollen(RolleSuchkriterien filter, Sortierung sortierung,
        Paginierung paginierung) throws BenutzerverwaltungValidationException;

    /**
     * Legt eine neue Rolle an.
     *
     * @param rolleAnlegen Daten zum Anlegen der Rolle
     * @return die neue Rolle.
     * @throws BenutzerverwaltungValidationException falls die Eingabedaten ungültig sind.
     */
    RolleDaten legeRolleAn(RolleAnlegen rolleAnlegen) throws BenutzerverwaltungValidationException;

    /**
     * Ändert die Daten einer Rolle durch einen Administrator.
     *
     * @param rolleAendern Daten zur Änderung der Rolle
     * @return die aktualisierte Rolle.
     * @throws BenutzerverwaltungValidationException falls die Eingabedaten ungültig sind.
     */
    RolleDaten aendereRolle(RolleAendern rolleAendern) throws BenutzerverwaltungValidationException;

    /**
     * Löscht eine Rolle anhand ihrer ID.
     *
     * @param rolleId ID der Rolle
     * @throws BenutzerverwaltungBusinessException falls die Eingabedaten ungültig sind.
     */
    void loescheRolle(String rolleId) throws BenutzerverwaltungBusinessException;

}
