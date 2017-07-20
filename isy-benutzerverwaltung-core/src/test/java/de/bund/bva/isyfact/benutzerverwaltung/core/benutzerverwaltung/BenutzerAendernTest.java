package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung;

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


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.PasswortAendern;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.PasswortZuruecksetzen;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.RollenTestdaten;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Testet das Ändern von Benutzern.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerAendernTest extends AbstractBenutzerverwaltungTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DatabaseSetup("testBenutzerverwaltungSetup.xml")
    public void testSetzePasswort() throws BenutzerverwaltungBusinessException {

        PasswortAendern passwortAendern =
            new PasswortAendern("benutzer", "passwort", "passwort_neu", "passwort_neu");
        BenutzerDaten benutzerDaten = benutzerverwaltung.setzePasswort(passwortAendern);

        assertTrue(passwordEncoder.matches("passwort_neu", benutzerDaten.getPasswort()));
    }

    @Test
    @DatabaseSetup("testBenutzerverwaltungSetup.xml")
    public void testSetzePasswortZurück() throws BenutzerverwaltungBusinessException {

        PasswortZuruecksetzen passwortZuruecksetzen =
            new PasswortZuruecksetzen("benutzer", "passwort_neu", "passwort_neu");
        BenutzerDaten benutzerDaten = benutzerverwaltung.setzePasswortZurueck(passwortZuruecksetzen);

        assertTrue(passwordEncoder.matches("passwort_neu", benutzerDaten.getPasswort()));
    }

    @Test
    @DatabaseSetup("testBenutzerverwaltungSetup.xml")
    @ExpectedDatabase(value = "testBenutzerverwaltungRolleZugewiesen.xml",
                      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testWeiseBenutzerRollenZu() throws BenutzerverwaltungBusinessException {
        List<String> benutzernamen = Arrays.asList("benutzer", "admin");
        RolleDaten rolle =
            new RolleDaten(RollenTestdaten.ROLLE_MODERATOR_ID, RollenTestdaten.ROLLE_MODERATOR_NAME);

        benutzerverwaltung.weiseRolleZu(rolle, benutzernamen);
    }

    @Test
    @DatabaseSetup("testBenutzerverwaltungSetup.xml")
    @ExpectedDatabase(value = "testBenutzerverwaltungRolleEntzogen.xml",
                      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testEntzieheBenutzerRolle() throws BenutzerverwaltungBusinessException {
        List<String> benutzernamen = Arrays.asList("benutzer", "admin");
        RolleDaten rolle =
            new RolleDaten(RollenTestdaten.ROLLE_MITGLIED_ID, RollenTestdaten.ROLLE_MITGLIED_NAME);

        benutzerverwaltung.entzieheRolle(rolle, benutzernamen);
    }
}
