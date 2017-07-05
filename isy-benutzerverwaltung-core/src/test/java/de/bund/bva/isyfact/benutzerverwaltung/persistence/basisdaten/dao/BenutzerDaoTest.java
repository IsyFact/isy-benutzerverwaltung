package de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao;

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
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.KonfigurationsSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.core.TestfallKonfiguration;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSuchkriterien;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Benutzer;
import de.bund.bva.isyfact.test.AbstractSpringDbUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@ContextConfiguration(classes = TestfallKonfiguration.class)
@DatabaseSetup("BenutzerverwaltungPersistenceTestSetup.xml")
public class BenutzerDaoTest extends AbstractSpringDbUnitTest {

    @Autowired
    protected BenutzerDao benutzerDao;

    @Autowired
    private RollenDao rollenDao;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void testSucheMitBenutzerFilterCaseSensitive() {
        when(TestfallKonfiguration.konfiguration.getAsBoolean(KonfigurationsSchluessel.SUCHE_CASE_SENSITIVE, false)).thenReturn(true);

        BenutzerSuchkriterien benutzerFilter = new BenutzerSuchkriterien();
        benutzerFilter.setBenutzername("test");
        benutzerFilter.setStatus(BenutzerStatus.AKTIVIERT);

        Sortierung sortierung =
            new Sortierung(BenutzerSortierattribut.BENUTZERNAME, Sortierrichtung.ABSTEIGEND);

        Paginierung paginierung = new Paginierung(0, 100);

        List<Benutzer> benutzerListe =
            benutzerDao.sucheMitBenutzerFilter(benutzerFilter, sortierung, paginierung);

        assertEquals(1, benutzerListe.size());
    }

    @Test
    public void testSucheMitBenutzerFilterCaseInsensitive() {
        when(TestfallKonfiguration.konfiguration.getAsBoolean(KonfigurationsSchluessel.SUCHE_CASE_SENSITIVE, false)).thenReturn(false);

        BenutzerSuchkriterien benutzerFilter = new BenutzerSuchkriterien();
        benutzerFilter.setBenutzername("test");
        benutzerFilter.setStatus(BenutzerStatus.AKTIVIERT);

        Sortierung sortierung =
            new Sortierung(BenutzerSortierattribut.BENUTZERNAME, Sortierrichtung.ABSTEIGEND);

        Paginierung paginierung = new Paginierung(0, 100);

        List<Benutzer> benutzerListe =
            benutzerDao.sucheMitBenutzerFilter(benutzerFilter, sortierung, paginierung);

        assertEquals(2, benutzerListe.size());
    }

    @Test
    public void testSucheBenutzerZuRollen() {
        BenutzerSuchkriterien benutzerFilter = new BenutzerSuchkriterien();
        benutzerFilter.setRollenId("idrolle1");

        Sortierung sortierung = new Sortierung(BenutzerSortierattribut.NACHNAME, Sortierrichtung.ABSTEIGEND);
        Paginierung paginierung = new Paginierung(0, 100);

        List<Benutzer> benutzerListe =
            benutzerDao.sucheMitBenutzerFilter(benutzerFilter, sortierung, paginierung);

        assertEquals(1, benutzerListe.size());
    }
}
