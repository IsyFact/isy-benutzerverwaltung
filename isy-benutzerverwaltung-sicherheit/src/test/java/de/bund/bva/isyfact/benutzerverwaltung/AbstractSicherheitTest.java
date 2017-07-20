package de.bund.bva.isyfact.benutzerverwaltung;

/*-
 * #%L
 * IsyFact Benutzerverwaltung Sicherheit
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

import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.BenutzerDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.RollenDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Benutzer;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Rolle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * Abstrakte Testklasse für Tests der Sicherheitskomponente.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
@ContextConfiguration(classes = TestfallKonfiguration.class)
public abstract class AbstractSicherheitTest extends AbstractTransactionalJUnit4SpringContextTests {

    protected static final String ROLLE_A = "Rolle_A";

    protected static final String ROLLE_B = "Rolle_B";

    protected static final String RECHT_B = "Recht_B";

    protected static final String RECHT_A = "Recht_A";

    @Autowired
    protected BenutzerDao benutzerDao;

    @Autowired
    protected RollenDao rollenDao;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    protected Benutzer erzeugeBenutzerInDb(String benutzername, String passwort, String emailAdresse, BenutzerStatus status,
        String... rollen) {
        Benutzer benutzer = new Benutzer();
        benutzer.setBenutzername(benutzername);

        // Setze Passwort
        String passwortHash = passwordEncoder.encode(passwort);
        benutzer.setPasswort(passwortHash);
        benutzer.setEmailAdresse(emailAdresse);
        benutzer.setStatus(status);

        for (String rolle : rollen) {
            Rolle neueRolle = new Rolle();
            neueRolle.setId(rolle);
            neueRolle.setName(rolle);
            rollenDao.speichere(neueRolle);
            benutzer.getRollen().add(neueRolle);
        }

        benutzerDao.speichere(benutzer);
        return benutzer;
    }

}
