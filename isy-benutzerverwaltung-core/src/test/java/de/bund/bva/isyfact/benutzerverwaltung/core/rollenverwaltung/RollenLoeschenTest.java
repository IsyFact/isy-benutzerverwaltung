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
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungTechnicalRuntimeException;
import org.junit.Test;

/**
 * Testet das Löschen von Rollen, sowohl erfolgreich als auch mit Fehlern bei der Validierung.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class RollenLoeschenTest extends AbstractRollenverwaltungTest {

    @Test
    @DatabaseSetup("testRollenverwaltungSetup.xml")
    @ExpectedDatabase(value = "testRollenverwaltungLoeschen.xml",
                      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testLoescheRolle() throws BenutzerverwaltungBusinessException {
        rollenverwaltung.loescheRolle(RollenTestdaten.ROLLE_MODERATOR_ID);
    }

    @Test(expected = BenutzerverwaltungBusinessException.class)
    @DatabaseSetup("testRollenverwaltungSetup.xml")
    public void testLoescheRolleNichtVorhanden() throws BenutzerverwaltungBusinessException {
        rollenverwaltung.loescheRolle("gibtesnicht");
    }

    @Test(expected = BenutzerverwaltungBusinessException.class)
    @DatabaseSetup("testRollenverwaltungSetup.xml")
    public void testLoescheRolleLeererName() throws BenutzerverwaltungBusinessException {
        rollenverwaltung.loescheRolle("");
    }

    @Test(expected = BenutzerverwaltungTechnicalRuntimeException.class)
    @DatabaseSetup("testRollenverwaltungSetup.xml")
    public void testLoescheRolleNull() throws BenutzerverwaltungBusinessException {
        rollenverwaltung.loescheRolle(null);
    }

}
