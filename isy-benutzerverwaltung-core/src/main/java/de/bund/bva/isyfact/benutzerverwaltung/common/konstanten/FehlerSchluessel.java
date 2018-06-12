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
 * Die Fehler Schluessel, die diese Konstante fuer Exceptions auf die Fehler.properties abbildet.
 *
 * @author Capgemini, Jonas Zitz
 */
public class FehlerSchluessel {

    /**
     * Es ist ein technischer Fehler aufgetreten {0}. Bitte wenden Sie sich an den/die Administrator/in des
     * Systems. Referenzcode: {1}
     */
    public static final String ALLGEMEINER_TECHNISCHER_FEHLER = "BENVZ99999";

    /**
     * Authentifzierung fehlgeschlagen.
     */
    public static final String MSG_AUTHENTIFIZIERUNG_FEHLGESCHLAGEN = "BENVZ00001";

    /**
     * Benutzer Account gesperrt.
     */
    public static final String MSG_BENUTZER_GESPERRT = "BENVZ00002";

    /**
     * Benutzername oder Passwort nicht korrekt.
     */
    public static final String MSG_BENUTZER_PASSWORT_FALSCH = "BENVZ00003";

    /**
     * Benutzer Account abgelaufen.
     */
    public static final String MSG_BENUTZER_ABGELAUFEN = "BENVZ00004";

    /**
     * Der Benutzer kann nicht gelöscht werden, da in der Anwendung noch auf ihn verwiesen wird.
     */
    public static final String BENUTZER_LOESCHEN_NICHT_MOEGLICH = "BENVW10100";

    /**
     * Der Benutzer kann nicht gelöscht werden, da in der Anwendung noch auf ihn verwiesen wird.
     */
    public static final String ROLLE_LOESCHEN_NICHT_MOEGLICH = "BENVW10101";

    /**
     * Technischer Fehler in der Datenbank.
     */
    public static final String MSG_TECHNISCHER_FEHLER_DATENBANK = "BENVW10900";

    /**
     * Technischer Fehler beim Konvertieren.
     */
    public static final String MSG_TECHNISCHER_FEHLER_KONVERTIERUNG = "BENVW10901";

    /**
     * Email versenden ist fehlgeschlagen
     */
    public static final String MSG_EMAIL_VERSENDEN_IST_FEHLGESCHLAGEN = "BENVW10902";

    /**
     * Validieren der Eingabedaten fehlgeschlagen.
     */
    public static final String MSG_EINGABEDATEN_UNGUELTIG = "BENVW10910";

    /**
     * Eingabedaten fehlen.
     */
    public static final String MSG_EINGABEDATEN_FEHLEN = "BENVW10911";

    /**
     * Token nicht gefunden.
     */
    public static final String MSG_TOKEN_NICHT_GEFUNDEN = "BENVW10912";

    /**
     * Tokendatum nicht gefunden.
     */
    public static final String MSG_TOKEN_DATUM_ABGELAUFEN = "BENVW10913";



}
