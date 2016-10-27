package de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao;

import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.RolleSuchkriterien;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Rolle;
import de.bund.bva.pliscommon.persistence.dao.Dao;

import java.util.Collection;
import java.util.List;

/**
 * Beschreibt den Datenbankzugriff für Rollen.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
public interface RollenDao extends Dao<Rolle, Long> {

    /**
     * Sucht eine Rolle anhand ihrer ID.
     *
     * @param rollenId ID der Rolle
     * @return die Rolle, falls eine gefunden wurde, oder {@code null}.
     */
    Rolle sucheMitRollenId(String rollenId);

    /**
     * Sucht eine Menge von Rollen anhand ihrer IDs.
     *
     * @param rollenIds IDs der Rollen
     * @return eine Liste aller Rollen, die zu den IDs gefunden wurden.
     */
    List<Rolle> sucheMitRollenIds(Collection<String> rollenIds);

    /**
     * Diese Methode filtert und sortiert eine Treffermenge an Rollen und gibt diese zurueck.
     *
     * @param suchkriterien Suchkriterien, nach denen gefiltert wird
     * @param sortierung    Sortierung der Ergebnisliste (Attribut und Richtung)
     * @param paginierung   Informationen zur Paginierung
     * @return anhand der Kriterien gefilterte, sortierte und gemäß der Paginierung geschnittene
     * Ergebnisliste.
     */
    List<Rolle> sucheMitFilter(RolleSuchkriterien suchkriterien, Sortierung sortierung,
        Paginierung paginierung);

    /**
     * Zählt die Benutzer, welche den Suchkriterien entsprechen.
     *
     * @param suchkriterien Suchkriterien
     * @return die Anzahl solcher Benutzer.
     */
    long zaehleMitKriterien(RolleSuchkriterien suchkriterien);
}
