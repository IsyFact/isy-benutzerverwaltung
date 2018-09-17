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

import java.util.Calendar;
import java.util.Date;

import de.bund.bva.isyfact.benutzerverwaltung.AbstractSicherheitTest;
import de.bund.bva.isyfact.benutzerverwaltung.TestfallKonfiguration;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.KonfigurationsSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.PasswortAendern;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.passwortpolicy.impl.DefaultPasswortPolicy;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Benutzer;
import de.bund.bva.isyfact.benutzerverwaltung.sicherheit.exception.BenutzerverwaltungAuthentifizierungFehlgeschlagenException;
import de.bund.bva.pliscommon.sicherheit.Sicherheit;
import de.bund.bva.pliscommon.sicherheit.common.exception.AuthentifizierungFehlgeschlagenException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Testet den Access Manager der Benutzerverwaltung
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerverwaltungAccessManagerTest extends AbstractSicherheitTest {

    private static final String BENUTZERNAME = "Benutzer_1";

    private static final String PASSWORT_ALT = "qwertZ1!";

    private static final String PASSWORT_NEU = "qwertZ1!1";

    private static final String PASSWORT = "qwertZ1!";

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

    @Before
    public void setup() {
        when(TestfallKonfiguration.konfiguration
            .getAsInteger(KonfigurationsSchluessel.ZUGRIFFSVERWALTUNG_MAX_FEHLANMELDEVERSUCHE, 5))
            .thenReturn(5);
        when(TestfallKonfiguration.konfiguration
            .getAsInteger(KonfigurationsSchluessel.ZUGRIFFSVERWALTUNG_BENUTZER_ABLAUFFRIST_IN_TAGEN, 365))
            .thenReturn(365);
        when(TestfallKonfiguration.konfiguration
            .getAsString(KonfigurationsSchluessel.PASSWORT_POLICY_SONDERZEICHEN,
                DefaultPasswortPolicy.DEFAULT_SONDERZEICHEN))
            .thenReturn(DefaultPasswortPolicy.DEFAULT_SONDERZEICHEN);
    }

    @Test
    public void testPasswortAenderung() throws BenutzerverwaltungBusinessException {
        erzeugeBenutzerInDb(BENUTZERNAME, PASSWORT_ALT, "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT,
            null, "Administrator");
        assertNotNull(sicherheit.getBerechtigungsManagerUndAuthentifiziere(
            erzeugeAufrufkontext(BENUTZERNAME, PASSWORT_ALT, false)));

        BenutzerDaten gesuchterBenutzer = benutzerverwaltung.leseBenutzer(BENUTZERNAME);
        PasswortAendern passwortAendern =
            new PasswortAendern(BENUTZERNAME, PASSWORT_ALT, PASSWORT_NEU, PASSWORT_NEU);
        benutzerverwaltung.setzePasswort(passwortAendern);

        try {
            sicherheit.getBerechtigungsManagerUndAuthentifiziere(
                erzeugeAufrufkontext(BENUTZERNAME, PASSWORT_ALT, false));
            fail("Benutzer wurde mit altem Passwort authentifziert.");
        } catch (AuthentifizierungFehlgeschlagenException ex) {
            // Success
        }
        assertNotNull(sicherheit.getBerechtigungsManagerUndAuthentifiziere(
            erzeugeAufrufkontext(BENUTZERNAME, PASSWORT_NEU, false)));
    }

    @Test(expected = AuthentifizierungFehlgeschlagenException.class)
    public void testBenutzerDeaktiviert() throws BenutzerverwaltungBusinessException {
        erzeugeBenutzerInDb(BENUTZERNAME, PASSWORT, "meine@emailAdresse.de", BenutzerStatus.DEAKTIVIERT,
            null);
        BenutzerDaten b = benutzerverwaltung.leseBenutzer(BENUTZERNAME);

        assertEquals(BenutzerStatus.DEAKTIVIERT, b.getStatus());

        sicherheit.getBerechtigungsManagerUndAuthentifiziere(
            erzeugeAufrufkontext(b.getBenutzername(), b.getPasswort(), true));
    }

    @Test
    public void testInkrementAnzahlFehlversuche() throws BenutzerverwaltungBusinessException {
        erzeugeBenutzerInDb(BENUTZERNAME, PASSWORT, "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT, null);
        assertNotNull(sicherheit
            .getBerechtigungsManagerUndAuthentifiziere(erzeugeAufrufkontext(BENUTZERNAME, PASSWORT, false)));

        // Erzeuge fehlgeschlagene Zugriffe
        int MAX_FEHLVERSUCHE_DEFAULT = 5;
        for (int i = 0; i < MAX_FEHLVERSUCHE_DEFAULT; i++) {
            try {
                sicherheit.getBerechtigungsManagerUndAuthentifiziere(
                    erzeugeAufrufkontext(BENUTZERNAME, "falschesPasswort", false));
                fail("Benutzer hat sich erfolgreich authentifiziert.");
            } catch (AuthentifizierungFehlgeschlagenException ex) {
                // OK
            }
        }
        try {
            sicherheit.getBerechtigungsManagerUndAuthentifiziere(
                erzeugeAufrufkontext(BENUTZERNAME, PASSWORT, false));
            fail("Benutzer sollte gesperrt sein");
        } catch (AuthentifizierungFehlgeschlagenException e) {
            // OK
        }

        BenutzerDaten bNachAuth = benutzerverwaltung.leseBenutzer(BENUTZERNAME);
        assertEquals(bNachAuth.getStatus(), BenutzerStatus.GESPERRT);
    }

    @Test(expected = AuthentifizierungFehlgeschlagenException.class)
    public void testBenutzerAbgelaufenAuthentifizierungSchlaegtFehl() {
        when(TestfallKonfiguration.konfiguration
            .getAsInteger(KonfigurationsSchluessel.ZUGRIFFSVERWALTUNG_BENUTZER_ABLAUFFRIST_IN_TAGEN, 365))
            .thenReturn(-1);

        erzeugeBenutzerInDb(BENUTZERNAME, PASSWORT, "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT, new Date());

        sicherheit.getBerechtigungsManagerUndAuthentifiziere(erzeugeAufrufkontext(BENUTZERNAME, PASSWORT, false));
    }

    @Test
    public void testBenutzerAbgelaufenBenutzerWirdGesperrtUndAnmeldedatumZurueckgesetzt() throws BenutzerverwaltungBusinessException {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, -2);
        Date letztesAnmeldedatum = c.getTime();
        erzeugeBenutzerInDb(BENUTZERNAME, PASSWORT, "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT, letztesAnmeldedatum);

        try {
            sicherheit.getBerechtigungsManagerUndAuthentifiziere(
                erzeugeAufrufkontext(BENUTZERNAME, PASSWORT, false));
        } catch (Exception e) {
            // leer
        }

        BenutzerDaten gesperrterBenutzer = benutzerverwaltung.leseBenutzer(BENUTZERNAME);

        assertEquals(BenutzerStatus.GESPERRT, gesperrterBenutzer.getStatus());
        assertNotEquals(gesperrterBenutzer.getLetzteAnmeldung(), letztesAnmeldedatum);
    }

    @Test
    public void testAuthentifizierungErfolgreichMitHash() throws BenutzerverwaltungBusinessException {
        Benutzer bDb =
            erzeugeBenutzerInDb(BENUTZERNAME, PASSWORT, "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT,
                null);
        BenutzerDaten b = benutzerverwaltung.leseBenutzer(BENUTZERNAME);

        String passwortHash = bDb.getPasswort();

        assertNotNull(sicherheit.getBerechtigungsManagerUndAuthentifiziere(
            erzeugeAufrufkontext(b.getBenutzername(), passwortHash, true)));
    }

    @Test
    public void testAuthentifizierungErfolgreich() throws BenutzerverwaltungBusinessException {
        erzeugeBenutzerInDb(BENUTZERNAME, PASSWORT, "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT, null);
        BenutzerDaten b = benutzerverwaltung.leseBenutzer(BENUTZERNAME);

        assertNotNull(sicherheit.getBerechtigungsManagerUndAuthentifiziere(
            erzeugeAufrufkontext(b.getBenutzername(), PASSWORT, false)));
    }

    @Test(expected = AuthentifizierungFehlgeschlagenException.class)
    public void testAuthentifizierungFehlgeschlagenFalschesPasswort()
        throws BenutzerverwaltungBusinessException {
        erzeugeBenutzerInDb(BENUTZERNAME, PASSWORT, "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT, null);
        BenutzerDaten b = benutzerverwaltung.leseBenutzer(BENUTZERNAME);

        sicherheit.getBerechtigungsManagerUndAuthentifiziere(
            erzeugeAufrufkontext(b.getBenutzername(), "geheim987", false));
    }

    @Test(expected = AuthentifizierungFehlgeschlagenException.class)
    public void testAuthentifizierungFehlerFalscherName() {
        erzeugeBenutzerInDb(BENUTZERNAME, PASSWORT, "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT, null);
        sicherheit
            .getBerechtigungsManagerUndAuthentifiziere(erzeugeAufrufkontext("Benutzer_99", PASSWORT, false));
    }

    @Test(expected = AuthentifizierungFehlgeschlagenException.class)
    public void testAuthentifizierungFehlerKeinPasswort() {
        erzeugeBenutzerInDb(BENUTZERNAME, PASSWORT, "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT, null);
        sicherheit.getBerechtigungsManagerUndAuthentifiziere(erzeugeAufrufkontext(BENUTZERNAME, "", false));
    }

    @Test(expected = AuthentifizierungFehlgeschlagenException.class)
    public void testAuthentifizierungFehlerNullPasswort() {
        erzeugeBenutzerInDb(BENUTZERNAME, PASSWORT, "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT, null);
        sicherheit.getBerechtigungsManagerUndAuthentifiziere(erzeugeAufrufkontext(BENUTZERNAME, null, false));
    }

    @Test(expected = AuthentifizierungFehlgeschlagenException.class)
    public void testAuthentifizierungFehlerKeinName() {
        erzeugeBenutzerInDb(BENUTZERNAME, PASSWORT, "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT, null);
        sicherheit.getBerechtigungsManagerUndAuthentifiziere(erzeugeAufrufkontext("", PASSWORT, false));
    }

    @Test(expected = AuthentifizierungFehlgeschlagenException.class)
    public void testAuthentifizierungFehlerNullName() {
        erzeugeBenutzerInDb(BENUTZERNAME, PASSWORT, "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT, null);
        sicherheit.getBerechtigungsManagerUndAuthentifiziere(erzeugeAufrufkontext(null, PASSWORT, false));
    }

    @Test
    public void logoutTest() throws BenutzerverwaltungBusinessException,
        BenutzerverwaltungAuthentifizierungFehlgeschlagenException {
        erzeugeBenutzerInDb(BENUTZERNAME, PASSWORT, "meine@emailAdresse.de", BenutzerStatus.AKTIVIERT, null);
        BenutzerDaten b = benutzerverwaltung.leseBenutzer(BENUTZERNAME);
        assertNull(b.getLetzteAbmeldung());

        benutzerverwaltung.speichereAbmeldung(b.getBenutzername());

        assertNotNull(benutzerverwaltung.leseBenutzer(BENUTZERNAME).getLetzteAbmeldung());
    }
}
