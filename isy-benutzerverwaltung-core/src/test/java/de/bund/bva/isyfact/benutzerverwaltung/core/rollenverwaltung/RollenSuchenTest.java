package de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung;

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
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierrichtung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Suchergebnis;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;
import org.junit.Test;

import static org.junit.Assert.*;

public class RollenSuchenTest extends AbstractRollenverwaltungTest {

    private static final String NEUE_ROLLE_ID = "TUERSTEHER";

    private static final String NEUE_ROLLE_NAME = "Administrator-Rolle mit Rechten zum Löschen von Benutzern";

    @Test
    @DatabaseSetup("testRollenverwaltungSetup.xml")
    public void testLeseRolle() throws BenutzerverwaltungBusinessException {
        RolleDaten rolle = rollenverwaltung.leseRolle(RollenTestdaten.ROLLE_ADMINISTRATOR_ID);
        assertNotNull(rolle);
        assertEquals(RollenTestdaten.ROLLE_ADMINISTRATOR_ID, rolle.getRollenId());
        assertEquals(RollenTestdaten.ROLLE_ADMINISTRATOR_NAME, rolle.getRollenName());
    }

    @Test(expected = BenutzerverwaltungBusinessException.class)
    @DatabaseSetup("testRollenverwaltungSetup.xml")
    public void testLeseRolleNichtVorhanden() throws BenutzerverwaltungBusinessException {
        rollenverwaltung.leseRolle("");
    }

    @Test
    @DatabaseSetup("testRollenverwaltungSetup.xml")
    public void testSucheAlleRollen() throws BenutzerverwaltungValidationException {
        Suchergebnis<RolleDaten> rollen = rollenverwaltung.sucheRollen(new RolleSuchkriterien(),
            new Sortierung(RolleSortierattribut.getStandard(), Sortierrichtung.getStandard()),
            new Paginierung(0, 10));
        assertNotNull(rollen);
        assertTrue(rollen.getAnzahlTreffer() == 3);
        for (RolleDaten rolleDaten : rollen.getTrefferliste()) {
            assertTrue(RollenTestdaten.ROLLEN_IDS.contains(rolleDaten.getRollenId()));
            assertTrue(RollenTestdaten.ROLLEN_NAMEN.contains(rolleDaten.getRollenName()));
        }
    }

}
