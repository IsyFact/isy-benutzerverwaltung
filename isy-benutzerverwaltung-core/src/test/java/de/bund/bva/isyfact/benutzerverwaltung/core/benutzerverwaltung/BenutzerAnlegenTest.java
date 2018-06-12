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
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.BenutzerAnlegen;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.RollenTestdaten;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Testet das Anlegen von Benutzern, sowohl erfolgreich als auch mit Fehlern bei der Validierung.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerAnlegenTest extends AbstractBenutzerverwaltungTest {

    @Test
    @DatabaseSetup("testBenutzerverwaltungSetup.xml")
    @ExpectedDatabase(value = "testBenutzerverwaltungSpeichern.xml",
                      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testlegeBenutzerAn() throws BenutzerverwaltungValidationException {
        BenutzerDaten benutzerDaten = benutzerverwaltung.legeBenutzerAn(neuerBenutzer());
        assertNotNull(benutzerDaten);
    }

    @Test(expected = BenutzerverwaltungValidationException.class)
    public void testBenutzerAnlegenUngueltigerName() throws BenutzerverwaltungValidationException {
        BenutzerAnlegen neuerBenutzer = neuerBenutzer();

        neuerBenutzer.setBenutzername("Benut!\"§$%&/()=ÜÄÖ");

        benutzerverwaltung.legeBenutzerAn(neuerBenutzer);
    }

    @Test(expected = BenutzerverwaltungValidationException.class)
    public void testBenutzerAnlegenOhneRolle() throws BenutzerverwaltungValidationException {
        BenutzerAnlegen neuerBenutzer = neuerBenutzer();

        neuerBenutzer.getRollen().clear();

        benutzerverwaltung.legeBenutzerAn(neuerBenutzer);
    }

    @Test(expected = BenutzerverwaltungValidationException.class)
    public void testBenutzerAnlegenFalscheRolle() throws BenutzerverwaltungValidationException {
        BenutzerAnlegen neuerBenutzer = neuerBenutzer();

        neuerBenutzer.getRollen().clear();
        neuerBenutzer.getRollen().add(new RolleDaten("Rolle_Falsch", "Gibt es nicht!"));

        benutzerverwaltung.legeBenutzerAn(neuerBenutzer);
    }

    @Test(expected = BenutzerverwaltungValidationException.class)
    public void testBenutzerAnlegenOhneName() throws BenutzerverwaltungValidationException {
        BenutzerAnlegen neuerBenutzer = neuerBenutzer();

        neuerBenutzer.setBenutzername(null);

        benutzerverwaltung.legeBenutzerAn(neuerBenutzer);
    }

    @Test(expected = BenutzerverwaltungValidationException.class)
    public void testBenutzerAnlegenDuplikat() throws BenutzerverwaltungValidationException {
        benutzerverwaltung.legeBenutzerAn(neuerBenutzer());
        benutzerverwaltung.legeBenutzerAn(neuerBenutzer());
    }

    private BenutzerAnlegen neuerBenutzer() {
        BenutzerAnlegen benutzer = new BenutzerAnlegen();
        benutzer.setBenutzername("moderator");
        benutzer.setPasswort("qwertzU1!");
        benutzer.setStatus(BenutzerStatus.AKTIVIERT);
        benutzer.setBehoerde("IHK");
        benutzer.setNachname("Mod");
        benutzer.setVorname("Erator");
        benutzer.setBemerkung("Vermittler");
        benutzer.setEmailAdresse("mod.erator@ihk.de");
        benutzer.setTelefonnummer("0177/4567890");

        benutzer.getRollen()
            .add(new RolleDaten(RollenTestdaten.ROLLE_MITGLIED_ID, RollenTestdaten.ROLLE_MITGLIED_NAME));
        benutzer.getRollen()
            .add(new RolleDaten(RollenTestdaten.ROLLE_MODERATOR_ID, RollenTestdaten.ROLLE_MODERATOR_NAME));

        return benutzer;
    }

}
