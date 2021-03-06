package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper;

/*-
 * #%L
 * IsyFact Benutzerverwaltung GUI mit Tomahawk
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

import java.util.List;

import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.Benutzerverwaltung;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.daten.BenutzerAnlegenDaten;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.daten.BenutzerBearbeitenSelbst;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzerbearbeiten.BenutzerBearbeitenModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzersuchen.BenutzerSuchkriterienModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.RolleModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.SuchergebnisModel;

/**
 * Anwendungskern-Wrapper fuer die Benutzerverwaltung-Komponente. Diese Komponente bietet die Moeglichkeit zur
 * Verwaltung (Auslesen, Erstellen, Bearbeiten, Loeschen) von {@link BenutzerModel Benutzern}. Weiterhin
 * biehtet sie die Funktionalitaet zur Verwaltung von Zuweisungen von Benutzern zu Rollen. Der Wrapper bietet
 * einen Zugriff auf die Core-Komponenten {@link Benutzerverwaltung}.
 *
 * @author Capgemini, Jonas Zitz
 * @version $Id: BenutzerverwaltungAwkWrapper.java 41878 2013-07-26 10:31:50Z jozitz $
 */
public interface BenutzerverwaltungAwkWrapper {

    /**
     * Liest einen Benutzer anhand des Benutzernamens aus.
     *
     * @param benutzername Benutzername
     * @return die Daten des Benutzers, falls vorhanden, oder {@code null}.
     * @throws BenutzerverwaltungBusinessException wenn der Benutzer nicht existiert.
     */
    BenutzerModel leseBenutzer(String benutzername) throws BenutzerverwaltungBusinessException;

    /**
     * Sucht Benutzer anhand von {@link BenutzerSuchkriterienModel Suchkriterien} und sortiert die Treffer
     * anschließend nach einem {@link BenutzerSortierattribut Sortierattribut}.
     *
     * @param filter      enthält die Suchkriterien
     * @param sortierung  enthält das Sortierattribut und die Reihenfolge der Sortierung (aufsteigend,
     *                    absteigend)
     * @param paginierung enthält den ersten Treffer und die maximale Größe der Trefferliste
     * @return eine sortierte Liste von Benutzern.
     * @throws BenutzerverwaltungValidationException wenn die Suchkriterien ungültig sind.
     */
    SuchergebnisModel<BenutzerModel> sucheBenutzer(BenutzerSuchkriterienModel filter, Sortierung sortierung,
        Paginierung paginierung) throws BenutzerverwaltungValidationException;

    /**
     * Legt einen neuen Benutzer an.
     *
     * @param benutzerAnlegenDaten Daten des neuen Benutzers
     * @return den neuen Benutzer.
     * @throws BenutzerverwaltungBusinessException falls die Eingabedaten ungültig sind.
     */
    BenutzerModel legeBenutzerAn(BenutzerAnlegenDaten benutzerAnlegenDaten)
        throws BenutzerverwaltungBusinessException;

    /**
     * Speichert die Abmeldung des Benutzers.
     *
     * @param benutzername Benutzername
     * @return den aktualisierten Benutzer.
     * @throws BenutzerverwaltungBusinessException wenn der Benutzer nicht existiert.
     */
    BenutzerModel speichereAbmeldung(String benutzername) throws BenutzerverwaltungBusinessException;
    
    /**
     * Ändert die Daten eines Benutzers durch einen Administrator.
     *
     * @param model das Model mit den geänderten Daten
     * @return den aktualisierten Benutzer.
     * @throws BenutzerverwaltungBusinessException falls die Eingabedaten ungültig sind.
     */
    BenutzerModel aendereBenutzer(BenutzerBearbeitenModel model) throws BenutzerverwaltungBusinessException;

    /**
     * Ändert die Daten eines Benutzers durch ihn selbst.
     *
     * @param benutzer Daten des Benutzers
     * @return den aktualisierten Benutzer.
     * @throws BenutzerverwaltungBusinessException falls die Eingabedaten ungültig sind.
     */
    BenutzerModel aendereBenutzerSelbst(BenutzerBearbeitenSelbst benutzer)
        throws BenutzerverwaltungBusinessException;
    
    /**
     * Setzt das Passwort eines Benutzers zurück.
     *
     * @param benutzername  Der Benutzername, für den das Passwort zurückgesetzt werden soll
     * @param neuesPasswort Das neue Passwort
     * @param neuesPasswortWiederholung Die Wiederholung des neuen Passworts
     * @throws BenutzerverwaltungValidationException falls die Eingabedaten ungültig sind.
     */
    void setzePasswortZurueck(String benutzername, String neuesPasswort, String neuesPasswortWiederholung)
        throws BenutzerverwaltungBusinessException;

    /**
     * Ändert das Passwort des Benutzers.
     *
     * @param benutzername  Der Benutzername, für den das Passwort zurückgesetzt werden soll
     * @param altesPasswort Das alte Passwort
     * @param neuesPasswort Das neue Passwort
     * @param neuesPasswortWiederholung Die Wiederholung des neuen Passworts
     * @throws BenutzerverwaltungValidationException falls die Eingabedaten ungültig sind.
     */
    void setzePasswort(String benutzername, String altesPasswort, String neuesPasswort, String neuesPasswortWiederholung)
        throws BenutzerverwaltungBusinessException;

    /**
     * Löscht einen Benutzer anhand des Benutzernamens.
     *
     * @param benutzer Benutzer
     * @throws BenutzerverwaltungBusinessException wenn die Eingabedaten ungültig sind.
     */
    void loescheBenutzer(BenutzerModel benutzer) throws BenutzerverwaltungBusinessException;

    /**
     * Weist einer Menge von Benutzern eine Rolle zu.
     *
     * @param rolle         Rolle
     * @param benutzernamen Benutzer, welche die Rolle erhalten sollen
     * @throws BenutzerverwaltungBusinessException falls die Rolle oder einer der Benutzer nicht existiert.
     */
    void weiseRolleZu(RolleModel rolle, List<String> benutzernamen)
        throws BenutzerverwaltungBusinessException;

    /**
     * Entzieht einer Menge von Benutzern eine Rolle.
     *
     * @param rolle         Rolle
     * @param benutzernamen Benutzer, welchen die Rolle entzogen werden soll.
     * @throws BenutzerverwaltungValidationException falls die Rolle oder einer der Benutzer nicht existiert.
     */
    void entzieheRolle(RolleModel rolle, List<String> benutzernamen)
        throws BenutzerverwaltungBusinessException;
}
