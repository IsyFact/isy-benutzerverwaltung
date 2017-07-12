package de.bund.bva.isyfact.benutzerverwaltung.gui.selfservice.awkwrapper;

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


import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.daten.PasswortZuruecksetzenDaten;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;

/**
 * Interface für den SelfServiceAwkWrapper.
 *
 * Mit dem SelfServiceAwkWrapper kann der Benutzer sein Passwort zurücksetzen, auch wenn er keine Admin-Rechte hat.
 *
 * @author Bjoern Saxe, msg systems ag
 * @author Alexander Salvanos, msg systems ag
 */
public interface SelfServiceAwkWrapper {
    /**
     * Sendet eine Email an den Benutzer, in der ein Link mit BenutzerToken enthalten ist.
     * Der Link mit BenutzerToken führt zu einer weiteren Ansicht, die das Zurücksetzen des Passworts ermöglicht.
     *
     * @param emailadresse die Emailadresse des Benutzers
     * @return  true, wenn die Email versandt wurde, sonst false
     * @throws BenutzerverwaltungBusinessException bei ungültiger Emailadresse
     */
    boolean sendePasswortZuruecksetzenEmail(String emailadresse) throws BenutzerverwaltungBusinessException;

    /**
     * Überprüft, ob das BenutzerToken abgelaufen oder nicht vorhanden ist.
     *
     * @param token Der String mit dem der Self Service eindeutig identifiziert wird.
     * @throws BenutzerverwaltungValidationException
     */
    String holeBenutzernameZuToken(String token) throws BenutzerverwaltungBusinessException;

    /**
     * Setzt das Passwort des Benutzers zurück
     *
     * @param passwortZuruecksetzenDaten Die Eingabedaten für das Zurücksetzen des Passworts
     * @return die Daten des Benutzers mit dem geänderten Passwort
     * @throws BenutzerverwaltungValidationException bei Eingabedaten falsch sind
     */
    BenutzerModel passwortZuruecksetzen(PasswortZuruecksetzenDaten passwortZuruecksetzenDaten) throws BenutzerverwaltungBusinessException;
}
