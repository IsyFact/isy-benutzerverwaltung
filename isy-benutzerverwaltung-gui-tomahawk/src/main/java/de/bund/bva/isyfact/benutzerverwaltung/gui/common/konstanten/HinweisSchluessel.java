package de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten;

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

/**
 * Konstanten für den Zugriff auf die Schlüssel von Hinweisen.
 *
 * @author msg systems, Stefan Dellmuth
 */
public abstract class HinweisSchluessel {

    /**
     * Der Benutzer mit Benutzernamen '{0}' wurde erfolgreich gelöscht.
     */
    public static final String BENUTZER_GELOESCHT = "BENVW80001";

    /**
     * Der Benutzer mit Benutzernamen '{0}' wurde erstellt.
     */
    public static final String BENUTZER_ERSTELLT = "BENVW80003";

    /**
     * Der Benutzer mit Benutzernamen '{0}' wurde aktualisiert.
     */
    public static final String BENUTZER_AKTUALISIERT = "BENVW80004";

    /**
     * Die gewählten Benutzer wurden erfolgreich aktualisiert.
     */
    public static final String ROLLENZUWEISUNG_BENUTZER_AKTUALISIERT = "BENVW80005";

    /**
     * Das Passwort des gewählten Benutzers wurde erfolgreich zurückgesetzt.
     */
    public static final String BENUTZER_PASSWORT_ZURUECKGESETZT = "BENVW80006";

    /**
     * Der Benutzer hat sein Passwort selbst geändert.
     */
    public static final String BENUTZER_SELBST_PASSWORT_GEAENDERT = "BENVW80007";

    /**
     * Die Rolle '{0}' wurde erfolgreich erstellt.
     */
    public static final String ROLLE_ERSTELLT = "BENVW80011";

    /**
     * Die Rolle '{0}' wurde erfolgreich geändert.
     */
    public static final String ROLLE_GEAENDERT = "BENVW80012";

    private HinweisSchluessel() {
    }

}
