package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten;

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

import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation.PasswortKorrekt;

import javax.validation.constraints.NotNull;

/**
 * Eingabedaten für eine Änderung des Passworts durch den Benutzer selbst.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
@PasswortKorrekt
public class PasswortAendern extends PasswortZuruecksetzen {

    @NotNull
    private final String altesPasswort;

    /**
     * Erzeugt eine Instanz der Eingabedaten.
     *
     * @param benutzername              Benutzername
     * @param altesPasswort             altes Passwort
     * @param neuesPasswort             neues Passwort
     * @param neuesPasswortBestaetigung Bestätigung des neuen Passworts
     */
    public PasswortAendern(String benutzername, String altesPasswort, String neuesPasswort,
        String neuesPasswortBestaetigung) {
        super(benutzername, neuesPasswort, neuesPasswortBestaetigung);
        this.altesPasswort = altesPasswort;
    }

    @NotNull
    public String getAltesPasswort() {
        return altesPasswort;
    }

}
