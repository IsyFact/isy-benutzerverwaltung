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

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import de.bund.bva.isyfact.benutzerverwaltung.core.TestfallKonfiguration;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.BenutzerTokenDao;
import de.bund.bva.isyfact.test.AbstractSpringDbUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Date;

@ContextConfiguration(classes = TestfallKonfiguration.class)
@DatabaseSetup("BenutzerverwaltungPersistenceTestSetup.xml")
public class JpaBenutzerTokenDaoTest extends AbstractSpringDbUnitTest {

    @Autowired
    private BenutzerTokenDao benutzerTokenDao;

    @Test
    @ExpectedDatabase(value = "BenutzerverwaltungPersistenceTestLoescheTokenBenutzer.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testLoescheTokensFuerBenutzer() throws Exception {
        benutzerTokenDao.loescheTokensFuerBenutzer("test");
    }

    @Test
    @ExpectedDatabase(value = "BenutzerverwaltungPersistenceTestLoescheTokenBenutzer.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testLoescheTokensVorDatum() throws Exception {

        LocalDateTime localDateTime = LocalDateTime.of(2017, Month.JULY, 1, 1, 1);

        Date date = Date.from(localDateTime.toInstant(ZoneOffset.UTC));
        benutzerTokenDao.loescheTokensVorDatum(date);
    }
}