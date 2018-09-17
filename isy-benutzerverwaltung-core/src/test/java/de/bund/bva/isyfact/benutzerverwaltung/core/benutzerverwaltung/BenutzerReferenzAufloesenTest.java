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
import de.bund.bva.isyfact.benutzerverwaltung.integration.BenutzerReferenz;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * @author Björn Saxe, msg systems ag
 */
public class BenutzerReferenzAufloesenTest extends AbstractBenutzerverwaltungTest {

    private static final DateFormat format = new SimpleDateFormat("yy-MM-dd");

    @Test
    @DatabaseSetup("testBenutzerverwaltungSetup.xml")
    public void testLoeseReferenzAuf() throws ParseException {
        BenutzerReferenz benutzerReferenz = new BenutzerReferenz(201L);

        benutzerverwaltung.loeseBenutzerReferenzAuf(benutzerReferenz);

        assertNotNull(benutzerReferenz.getDaten());
        assertEquals(201L, (long)benutzerReferenz.getId());
        assertEquals(201L, (long)benutzerReferenz.getDaten().getId());

        assertEquals("benutzer", benutzerReferenz.getDaten().getBenutzername());
        assertEquals("$2a$10$FrhgE995.OkufUJ7YKlRHekMyTKRjzUAo54HkQ46ANlZibPY0n4ym", benutzerReferenz.getDaten().getPasswort());
        assertEquals("BFJA", benutzerReferenz.getDaten().getBehoerde());
        assertEquals("Tester", benutzerReferenz.getDaten().getNachname());
        assertEquals("Klaus", benutzerReferenz.getDaten().getVorname());
        assertEquals(BenutzerStatus.AKTIVIERT, benutzerReferenz.getDaten().getStatus());
        assertEquals(5, benutzerReferenz.getDaten().getFehlanmeldeVersuche());
        assertEquals(format.parse("2016-01-01"), benutzerReferenz.getDaten().getLetzteAnmeldung());
        assertEquals(format.parse("2016-01-02"), benutzerReferenz.getDaten().getLetzteAbmeldung());
        assertEquals("Testexperte", benutzerReferenz.getDaten().getBemerkung());
        assertEquals("klaus.tester@behoerde.de", benutzerReferenz.getDaten().getEmailAdresse());
        assertEquals("0123/456789", benutzerReferenz.getDaten().getTelefonnummer());
    }

    @Test
    @DatabaseSetup("testBenutzerverwaltungSetup.xml")
    public void testLoeseReferenzAufBenutzerDatenAktualisieren() {
        BenutzerReferenz benutzerReferenz = new BenutzerReferenz(201L);

        benutzerverwaltung.loeseBenutzerReferenzAuf(benutzerReferenz);

        assertNotNull(benutzerReferenz.getDaten());

        benutzerReferenz.setId(202L);

        benutzerverwaltung.loeseBenutzerReferenzAuf(benutzerReferenz);

        assertNotNull(benutzerReferenz.getDaten());
        assertEquals(202L, (long)benutzerReferenz.getDaten().getId());
    }
}
