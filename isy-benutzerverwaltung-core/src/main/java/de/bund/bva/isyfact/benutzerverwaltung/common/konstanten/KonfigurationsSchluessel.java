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


/**
 * @author jozitz
 * @version $Id: KonfigurationsSchluessel.java 41689 2013-07-16 14:03:32Z jozitz $
 */
public abstract class KonfigurationsSchluessel {

    public static final String ZUGRIFFSVERWALTUNG_MAX_FEHLANMELDEVERSUCHE =
            "benutzerverwaltung.maxfehlanmeldeversuche";

    public static final String ZUGRIFFSVERWALTUNG_BENUTZER_ABLAUFFRIST_IN_TAGEN = "benutzerverwaltung.benutzer.ablauffrist.tage";

    public static final String SELFSERVICE_TOKEN_ABGELAUFEN_IN_STUNDEN =
            "selfservice.token.timeout.hours";

    public static final String SUCHE_CASE_SENSITIVE = "benutzerverwaltung.suche.casesensitive";

    public static final String ANZAHL_SPEICHERE_LETZTE_PASSWOERTER = "benutzerverwaltung.benutzer.anzahl.letztepasswoerter";

    public static final String PASSWORT_POLICY_MINDESTLAENGE = "benutzerverwaltung.passwortpolicy.mindestlaenge";

    public static final String PASSWORT_POLICY_SONDERZEICHEN = "benutzerverwaltung.passwortpolicy.sonderzeichen";

    public static final String PASSWORT_POLICY_ANZAHL_LETZTE_PASSWOERTER = "benutzerverwaltung.passwortpolicy.anzahlletztepasswoerter";
}
