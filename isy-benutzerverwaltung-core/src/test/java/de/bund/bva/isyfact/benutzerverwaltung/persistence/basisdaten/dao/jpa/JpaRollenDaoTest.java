package de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.jpa;

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

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierrichtung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.KonfigurationsSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.core.TestfallKonfiguration;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.RolleSortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.RolleSuchkriterien;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.RollenDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Rolle;
import de.bund.bva.isyfact.test.AbstractSpringDbUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Björn Saxe, msg systems ag
 */

@ContextConfiguration(classes = TestfallKonfiguration.class)
@DatabaseSetup("BenutzerverwaltungPersistenceTestSetup.xml")
public class JpaRollenDaoTest extends AbstractSpringDbUnitTest {

    @Autowired
    private RollenDao rollenDao;

    @Test
    public void testSucheMitRollenId() throws Exception {
        Rolle ergebnis = rollenDao.sucheMitRollenId("idrolle1");

        assertEquals("idrolle1", ergebnis.getId());
    }

    @Test
    public void testSucheMitRollenIds() throws Exception {
        Set<Rolle> ergebnis = rollenDao.sucheMitRollenIds(Arrays.asList("idrolle1", "idrolle2"));

        assertEquals(2, ergebnis.size());
    }

    @Test
    public void testSucheMitFilterCaseSensitive() throws Exception {
        when(TestfallKonfiguration.konfiguration.getAsBoolean(KonfigurationsSchluessel.SUCHE_CASE_SENSITIVE, false)).thenReturn(true);

        RolleSuchkriterien suchkriterien = new RolleSuchkriterien();
        suchkriterien.setId("idrolle1");
        suchkriterien.setName("namerolle1");

        Sortierung sortierung =
            new Sortierung(RolleSortierattribut.ID, Sortierrichtung.ABSTEIGEND);

        Paginierung paginierung = new Paginierung(0, 100);

        List<Rolle> ergebnis = rollenDao.sucheMitFilter(suchkriterien, sortierung, paginierung);

        assertEquals(1, ergebnis.size());
        assertEquals("idrolle1", ergebnis.get(0).getId());
        assertEquals("namerolle1", ergebnis.get(0).getName());
    }

    @Test
    public void testSucheMitFilterCaseInsensitive() throws Exception {
        when(TestfallKonfiguration.konfiguration.getAsBoolean(KonfigurationsSchluessel.SUCHE_CASE_SENSITIVE, false)).thenReturn(false);

        RolleSuchkriterien suchkriterien = new RolleSuchkriterien();
        suchkriterien.setId("idrolle1");

        Sortierung sortierung =
            new Sortierung(RolleSortierattribut.ID, Sortierrichtung.ABSTEIGEND);

        Paginierung paginierung = new Paginierung(0, 100);

        List<Rolle> ergebnis = rollenDao.sucheMitFilter(suchkriterien, sortierung, paginierung);

        assertEquals(2, ergebnis.size());
    }
}