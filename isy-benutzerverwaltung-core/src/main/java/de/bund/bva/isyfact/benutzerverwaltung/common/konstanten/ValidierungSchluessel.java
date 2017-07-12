package de.bund.bva.isyfact.benutzerverwaltung.common.konstanten;

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

public class ValidierungSchluessel {

    /**
     * Ein Benutzer mit dem Namen {0} ist bereits vorhanden.
     */
    public static final String MSG_BENUTZERNAME_BEREITS_VORHANDEN =
        "validation.beanmanuell.benutzerbo.benutzername.bereitsVorhanden";

    /**
     * Ungültiger Benutzername: Es sind nur Alphanumerische Zeichen ('A-Z', 'a-z', '0-9'), Bindestriche ('-'),
     * Unterstriche ('_') und Punkte ('. ') erlaubt.
     */
    public static final String MSG_BENUTZERNAME_UNGUELTIG =
        "validation.beanmanuell.benutzerbo.benutzername.ungueltigeZeichen";

    /**
     * Benutzername nicht vorhanden.
     */
    public static final String MSG_BENUTZERNAME_NICHT_VORHANDEN =
        "validation.beanmanuell.benutzerbo.benutzername.nichtExistent";

    /**
     * Die Rolle mit dem Namen {0} ist nicht vorhanden.
     */
    public static final String MSG_ROLLE_NICHT_VORHANDEN =
        "validation.beanmanuell.benutzerbo.rolle.nichtExistent";

    /**
     * Bitte wählen Sie eine Rolle aus.
     */
    public static final String MSG_KEINE_ROLLE_AUSGEWAEHLT =
        "validation.beanmanuell.benutzerbo.rolle.nichtAusgewaehlt";

    /**
     * Das Password darf nicht leer sein.
     */
    public static final String MSG_PASSWORT_LEER = "validation.beanmanuell.benutzerbo.passwort.leer";

    public static final String MSG_PASSWORT_AENDERN_FALSCH = "validation.benutzer.passwort.aendern.falsch";

    public static final String MSG_PASSWORT_AENDERN_UNTERSCHIEDLICH =
            "validation.benutzer.passwort.unterschiedlich";

    public static final String MSG_EMAIL_UNGUELTIG = "validation.benutzer.email.ungueltig";

    public static final String MSG_ROLLE_BEREITS_VORHANDEN = "validation.rolle.vorhanden";

    public static final String ROLLE_ID_UNGUELTIG = "validation.rolle.id.ungueltig";

}
