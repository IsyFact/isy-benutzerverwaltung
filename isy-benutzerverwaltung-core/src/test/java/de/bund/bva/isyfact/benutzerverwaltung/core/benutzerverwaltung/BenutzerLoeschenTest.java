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
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungTechnicalRuntimeException;
import org.junit.Test;

/**
 * Testet das Löschen von Benutzern, sowohl erfolgreich als auch mit Fehlern bei der Validierung.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerLoeschenTest extends AbstractBenutzerverwaltungTest {

    @Test
    @DatabaseSetup("testBenutzerverwaltungSetup.xml")
    @ExpectedDatabase(value = "testBenutzerverwaltungLoeschen.xml",
                      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testLoescheBenutzer() throws BenutzerverwaltungBusinessException {
        benutzerverwaltung.loescheBenutzer("benutzer");
    }

    @Test(expected = BenutzerverwaltungBusinessException.class)
    @DatabaseSetup("testBenutzerverwaltungSetup.xml")
    public void testLoescheBenutzerNichtVorhanden() throws BenutzerverwaltungBusinessException {
        benutzerverwaltung.loescheBenutzer("gibtesnicht");
    }

    @Test(expected = BenutzerverwaltungBusinessException.class)
    @DatabaseSetup("testBenutzerverwaltungSetup.xml")
    public void testLoescheBenutzerLeererName() throws BenutzerverwaltungBusinessException {
        benutzerverwaltung.loescheBenutzer("");
    }

    @Test(expected = BenutzerverwaltungTechnicalRuntimeException.class)
    @DatabaseSetup("testBenutzerverwaltungSetup.xml")
    public void testLoescheBenutzerNull() throws BenutzerverwaltungBusinessException {
        benutzerverwaltung.loescheBenutzer(null);
    }

}
