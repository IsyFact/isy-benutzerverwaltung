package de.bund.bva.isyfact.benutzerverwaltung.sicherheit;

/*-
 * #%L
 * IsyFact Benutzerverwaltung Sicherheit
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


import de.bund.bva.isyfact.benutzerverwaltung.AbstractFfBenutzerzeichnisTest;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.PasswortAendern;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Benutzer;
import de.bund.bva.isyfact.benutzerverwaltung.sicherheit.exception.BenutzerverwaltungAuthentifizierungFehlgeschlagenException;
import de.bund.bva.pliscommon.sicherheit.Sicherheit;
import de.bund.bva.pliscommon.sicherheit.common.exception.AuthentifizierungFehlgeschlagenException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Testet den Access Manager der Benutzerverwaltung
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerverwaltungAccessManagerTest extends AbstractFfBenutzerzeichnisTest {

    @Autowired
    private GesichertBenutzerverwaltung benutzerverwaltung;

    @Autowired
    private Sicherheit<BenutzerverwaltungAufrufKontextImpl> sicherheit;

    private BenutzerverwaltungAufrufKontextImpl erzeugeAufrufkontext(String benutzername, String passwort,
        boolean hash) {
        BenutzerverwaltungAufrufKontextImpl aufrufKontext = new BenutzerverwaltungAufrufKontextImpl();
        aufrufKontext.setDurchfuehrenderBenutzerKennung(benutzername);
        aufrufKontext.setDurchfuehrenderBenutzerPasswort(passwort);
        aufrufKontext.setPasswortIstHash(hash);
        return aufrufKontext;
    }

    @Test
    public void testPasswortAenderung() throws BenutzerverwaltungBusinessException {
        erzeugeBenutzerInDb("Benutzer_1", "altalt123", "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT, "Administrator");
        assertNotNull(sicherheit.getBerechtigungsManagerUndAuthentifiziere(
            erzeugeAufrufkontext("Benutzer_1", "altalt123", false)));

        BenutzerDaten gesuchterBenutzer = benutzerverwaltung.leseBenutzer("Benutzer_1");
        PasswortAendern passwortAendern =
            new PasswortAendern("Benutzer_1", "altalt123", "neuneu123", "neuneu123");
        benutzerverwaltung.setzePasswort(passwortAendern);

        try {
            sicherheit.getBerechtigungsManagerUndAuthentifiziere(
                erzeugeAufrufkontext("Benutzer_1", "altalt123", false));
            fail("Benutzer wurde mit altem Passwort authentifziert.");
        } catch (AuthentifizierungFehlgeschlagenException ex) {
            // Success
        }
        assertNotNull(sicherheit.getBerechtigungsManagerUndAuthentifiziere(
            erzeugeAufrufkontext("Benutzer_1", "neuneu123", false)));
    }

    @Test(expected = AuthentifizierungFehlgeschlagenException.class)
    public void testBenutzerDeaktiviert() throws BenutzerverwaltungBusinessException {
        erzeugeBenutzerInDb("Benutzer_1", "geheim123", "meine@emailAdresse.de", BenutzerStatus.DEAKTIVIERT);
        BenutzerDaten b = benutzerverwaltung.leseBenutzer("Benutzer_1");

        assertEquals(BenutzerStatus.DEAKTIVIERT, b.getStatus());

        sicherheit.getBerechtigungsManagerUndAuthentifiziere(
            erzeugeAufrufkontext(b.getBenutzername(), b.getPasswort(), true));
    }

    @Test
    public void testInkrementAnzahlFehlversuche() throws BenutzerverwaltungBusinessException {
        erzeugeBenutzerInDb("Benutzer_1", "geheim123", "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT);
        assertNotNull(sicherheit.getBerechtigungsManagerUndAuthentifiziere(
            erzeugeAufrufkontext("Benutzer_1", "geheim123", false)));

        // Erzeuge fehlgeschlagene Zugriffe
        int MAX_FEHLVERSUCHE_DEFAULT = 5;
        for (int i = 0; i < MAX_FEHLVERSUCHE_DEFAULT + 1; i++) {
            try {
                sicherheit.getBerechtigungsManagerUndAuthentifiziere(
                    erzeugeAufrufkontext("Benutzer_1", "falschesPasswort", false));
                fail("Benutzer hat sich erfolgreich authentifiziert.");
            } catch (AuthentifizierungFehlgeschlagenException ex) {
                // OK
            }
        }
        try {
            sicherheit.getBerechtigungsManagerUndAuthentifiziere(
                erzeugeAufrufkontext("Benutzer_1", "geheim123", false));
            fail("Benutzer sollte gesperrt sein");
        } catch (AuthentifizierungFehlgeschlagenException e) {
            // OK
        }

        BenutzerDaten bNachAuth = benutzerverwaltung.leseBenutzer("Benutzer_1");
        assertEquals(bNachAuth.getStatus(), BenutzerStatus.GESPERRT);
    }

    @Test
    public void testAuthentifizierungErfolgreichMitHash() throws BenutzerverwaltungBusinessException {
        Benutzer bDb = erzeugeBenutzerInDb("Benutzer_1", "geheim123", "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT);
        BenutzerDaten b = benutzerverwaltung.leseBenutzer("Benutzer_1");

        String passwortHash = bDb.getPasswort();

        assertNotNull(sicherheit.getBerechtigungsManagerUndAuthentifiziere(
            erzeugeAufrufkontext(b.getBenutzername(), passwortHash, true)));
    }

    @Test
    public void testAuthentifizierungErfolgreich() throws BenutzerverwaltungBusinessException {
        erzeugeBenutzerInDb("Benutzer_1", "geheim123", "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT);
        BenutzerDaten b = benutzerverwaltung.leseBenutzer("Benutzer_1");

        assertNotNull(sicherheit.getBerechtigungsManagerUndAuthentifiziere(
            erzeugeAufrufkontext(b.getBenutzername(), "geheim123", false)));
    }

    @Test(expected = AuthentifizierungFehlgeschlagenException.class)
    public void testAuthentifizierungFehlgeschlagenFalschesPasswort()
        throws BenutzerverwaltungBusinessException {
        erzeugeBenutzerInDb("Benutzer_1", "geheim123", "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT);
        BenutzerDaten b = benutzerverwaltung.leseBenutzer("Benutzer_1");

        sicherheit.getBerechtigungsManagerUndAuthentifiziere(
            erzeugeAufrufkontext(b.getBenutzername(), "geheim987", false));
    }

    @Test(expected = AuthentifizierungFehlgeschlagenException.class)
    public void testAuthentifizierungFehlerFalscherName() {
        erzeugeBenutzerInDb("Benutzer_1", "geheim123", "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT);
        sicherheit.getBerechtigungsManagerUndAuthentifiziere(
            erzeugeAufrufkontext("Benutzer_99", "geheim123", false));
    }

    @Test(expected = AuthentifizierungFehlgeschlagenException.class)
    public void testAuthentifizierungFehlerKeinPasswort() {
        erzeugeBenutzerInDb("Benutzer_1", "geheim123", "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT);
        sicherheit.getBerechtigungsManagerUndAuthentifiziere(erzeugeAufrufkontext("Benutzer_1", "", false));
    }

    @Test(expected = AuthentifizierungFehlgeschlagenException.class)
    public void testAuthentifizierungFehlerNullPasswort() {
        erzeugeBenutzerInDb("Benutzer_1", "geheim123", "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT);
        sicherheit.getBerechtigungsManagerUndAuthentifiziere(erzeugeAufrufkontext("Benutzer_1", null, false));
    }

    @Test(expected = AuthentifizierungFehlgeschlagenException.class)
    public void testAuthentifizierungFehlerKeinName() {
        erzeugeBenutzerInDb("Benutzer_1", "geheim123", "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT);
        sicherheit.getBerechtigungsManagerUndAuthentifiziere(erzeugeAufrufkontext("", "geheim123", false));
    }

    @Test(expected = AuthentifizierungFehlgeschlagenException.class)
    public void testAuthentifizierungFehlerNullName() {
        erzeugeBenutzerInDb("Benutzer_1", "geheim123", "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT);
        sicherheit.getBerechtigungsManagerUndAuthentifiziere(erzeugeAufrufkontext(null, "geheim123", false));
    }

    @Test
    public void logoutTest() throws BenutzerverwaltungBusinessException,
        BenutzerverwaltungAuthentifizierungFehlgeschlagenException {
        erzeugeBenutzerInDb("Benutzer_1", "geheim123", "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT);
        BenutzerDaten b = benutzerverwaltung.leseBenutzer("Benutzer_1");
        assertNull(b.getLetzteAbmeldung());

        benutzerverwaltung.speichereAbmeldung(b.getBenutzername());

        assertNotNull(benutzerverwaltung.leseBenutzer("Benutzer_1").getLetzteAbmeldung());
    }
}
