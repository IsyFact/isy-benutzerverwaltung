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
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierrichtung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Suchergebnis;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Testet das Löschen von Benutzern, sowohl erfolgreich als auch mit Fehlern bei der Validierung.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerSuchenTest extends AbstractBenutzerverwaltungTest {

    @Test
    @DatabaseSetup("testBenutzerverwaltungSetup.xml")
    public void testSucheBenutzer() throws BenutzerverwaltungValidationException {
        Sortierung benutzerSortierungNach =
            new Sortierung(BenutzerSortierattribut.getStandard(), Sortierrichtung.getStandard());
        Paginierung paginierung = new Paginierung(0, 100);

        Suchergebnis<BenutzerDaten> suchergebnis = benutzerverwaltung
            .sucheBenutzer(new BenutzerSuchkriterien(), benutzerSortierungNach, paginierung);
        assertNotNull(suchergebnis);
        assertEquals(2, suchergebnis.getTrefferliste().size());
        assertEquals(2, suchergebnis.getAnzahlTreffer());
    }

    @Test
    @DatabaseSetup("testBenutzerverwaltungSetup.xml")
    public void testSucheBenutzerPaginierung() throws BenutzerverwaltungValidationException {
        Sortierung benutzerSortierungNach =
            new Sortierung(BenutzerSortierattribut.getStandard(), Sortierrichtung.getStandard());
        Paginierung paginierung = new Paginierung(0, 1);

        Suchergebnis<BenutzerDaten> suchergebnis = benutzerverwaltung
            .sucheBenutzer(new BenutzerSuchkriterien(), benutzerSortierungNach, paginierung);
        assertNotNull(suchergebnis);
        assertEquals(1, suchergebnis.getTrefferliste().size());
        assertEquals(2, suchergebnis.getAnzahlTreffer());
    }

}
