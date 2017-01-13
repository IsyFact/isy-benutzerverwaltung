package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper;

/*-
 * #%L
 * IsyFact Benutzerverwaltung GUI mit Primefaces
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
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.daten.BenutzerAnlegenDaten;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.daten.PasswortZuruecksetzenDaten;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzerbearbeiten.BenutzerBearbeitenModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzerselbstbearbeiten.BenutzerSelbstBearbeitenModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzersuchen.BenutzerSuchkriterienModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.passwortaendern.PasswortAendernModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.RolleModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.SuchergebnisModel;

import java.util.List;

/**
 * Wrapper für die Komponente Benutzerverwaltung des Anwendungskerns. Bietet die Möglichkeit zur Verwaltung
 * (Lesen, Suchen, Anlegen, Ändern, Löschen) von Benutzern sowie zum Zuweisen bzw. Entziehen von Rollen.
 *
 * @author msg systems ag, Stefan Dellmuth
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
     * Ändert das Passwort des Benutzers. Diese Aktion kann von Benutzern selbst durchgeführt werden.
     *
     * @param passwortAendernModel Model zur Änderung des Passworts
     * @return den aktualisierten Benutzer.
     * @throws BenutzerverwaltungBusinessException falls die Eingabedaten ungültig sind.
     */
    BenutzerModel setzePasswort(PasswortAendernModel passwortAendernModel)
        throws BenutzerverwaltungBusinessException;

    /**
     * Setzt das Passwort eines Benutzers zurück. Diese Aktion kann nur von einem Administrator durchgeführt
     * werden.
     *
     * @param passwortZuruecksetzenDaten Daten zum Zurücksetzen eines Passworts
     * @return den aktualisierten Benutzer.
     * @throws BenutzerverwaltungBusinessException falls die Eingabedaten ungültig sind.
     */
    BenutzerModel setzePasswortZurueck(PasswortZuruecksetzenDaten passwortZuruecksetzenDaten)
        throws BenutzerverwaltungBusinessException;

    /**
     * Setzt den Status einen Benutzers.
     *
     * @param benutzername Benutzername
     * @param neuerStatus  neuer Status
     * @return den aktualisierten Benutzer.
     * @throws BenutzerverwaltungBusinessException falls der Benutzer nicht existiert.
     */
    BenutzerModel setzeStatus(String benutzername, BenutzerStatus neuerStatus)
        throws BenutzerverwaltungBusinessException;

    /**
     * Ändert die Daten eines Benutzers durch einen Administrator.
     *
     * @param model Model des geänderten Benutzers
     * @return den aktualisierten Benutzer.
     * @throws BenutzerverwaltungBusinessException falls die Eingabedaten ungültig sind.
     */
    BenutzerModel aendereBenutzer(BenutzerBearbeitenModel model) throws BenutzerverwaltungBusinessException;

    /**
     * Ändert die Daten eines Benutzers durch ihn selbst.
     *
     * @param model Model des geänderten Benutzers
     * @return den aktualisierten Benutzer.
     * @throws BenutzerverwaltungBusinessException falls die Eingabedaten ungültig sind.
     */
    BenutzerModel aendereBenutzerSelbst(BenutzerSelbstBearbeitenModel model)
        throws BenutzerverwaltungBusinessException;

    /**
     * Löscht einen Benutzer anhand des Benutzernamens.
     *
     * @param benutzer Benutzer
     * @throws BenutzerverwaltungBusinessException wenn die Eingabedaten ungültig sind.
     */
    void loescheBenutzer(BenutzerModel benutzer) throws BenutzerverwaltungBusinessException;

    /**
     * Speichert die Abmeldung des Benutzers.
     *
     * @param benutzername Benutzername
     * @return den aktualisierten Benutzer.
     * @throws BenutzerverwaltungBusinessException wenn der Benutzer nicht existiert.
     */
    BenutzerModel speichereAbmeldung(String benutzername) throws BenutzerverwaltungBusinessException;

    /**
     * Weist einer Menge von Benutzern eine Rolle zu.
     *
     * @param model         Rolle
     * @param benutzernamen Benutzer, welche die Rolle erhalten sollen
     * @throws BenutzerverwaltungBusinessException falls die Rolle oder einer der Benutzer nicht existiert.
     */
    void weiseRolleZu(RolleModel model, List<String> benutzernamen)
        throws BenutzerverwaltungBusinessException;

    /**
     * Entzieht einer Menge von Benutzern eine Rolle.
     *
     * @param model         Rolle
     * @param benutzernamen Benutzer, welchen die Rolle entzogen werden soll.
     * @throws BenutzerverwaltungBusinessException falls die Rolle oder einer der Benutzer nicht existiert.
     */
    void entzieheRolle(RolleModel model, List<String> benutzernamen)
        throws BenutzerverwaltungBusinessException;

    /**
     * Liefert eine Liste mit allen im System vorhandenen Rollen.
     *
     * @return Liste mit Rollen
     */
    List<RolleModel> leseAlleRollen() throws BenutzerverwaltungValidationException;

}
