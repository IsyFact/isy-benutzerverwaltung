package de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao;

import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSuchkriterien;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Benutzer;
import de.bund.bva.pliscommon.persistence.dao.Dao;

import java.util.List;

/**
 * Beschreibt den Datenbankzugriff für Benutzer.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
public interface BenutzerDao extends Dao<Benutzer, Long> {

    /**
     * Liefert den Benutzer mit dem angegeben Benutzername.
     *
     * @param benutzername Suchparameter.
     * @return Benutzer mit dem Namen oder <code>null</code>
     */
    Benutzer sucheMitBenutzername(String benutzername);

    /**
     * Diese Methode filtert und sortiert eine Treffermenge an {@link Benutzer Benutzern} und gibt diese
     * zurueck.
     *
     * @param suchkriterien Suchkriterien, nach denen gefiltert wird
     * @param sortierung    Sortierung der Ergebnisliste (Attribut und Richtung)
     * @param paginierung   Informationen zur Paginierung
     * @return anhand der Kriterien gefilterte, sortierte und gemäß der Paginierung geschnittene
     * Ergebnisliste.
     */
    List<Benutzer> sucheMitBenutzerFilter(BenutzerSuchkriterien suchkriterien, Sortierung sortierung,
        Paginierung paginierung);

    /**
     * Zählt die Benutzer, welche den Suchkriterien entsprechen.
     *
     * @param suchkriterien Suchkriterien
     * @return die Anzahl solcher Benutzer.
     */
    long zaehleMitKriterien(BenutzerSuchkriterien suchkriterien);
}
