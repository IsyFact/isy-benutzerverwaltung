package de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao;

/*-
 * #%L
 * IsyFact Benutzerverwaltung Core
 * %%
 * Copyright (C) 2016 - 2017 Bundesverwaltungsamt (BVA)
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
