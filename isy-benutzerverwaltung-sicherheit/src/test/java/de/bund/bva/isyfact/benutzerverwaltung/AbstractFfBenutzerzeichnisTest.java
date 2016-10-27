package de.bund.bva.isyfact.benutzerverwaltung;

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
 * Abstracte Testklasse für FfBenutzerverzeichnis tests. Bietet einheitliche
 * Funktionen, wie das erzeugen eines neuen Benutzers.
 *
 * @author <a href="jonas.zitz@capgemini.com">Jonas Zitz</a>
 * @version $Revision: 41876 $
 */
@ContextConfiguration(classes = TestfallKonfiguration.class)
public abstract class AbstractFfBenutzerzeichnisTest extends AbstractTransactionalJUnit4SpringContextTests {

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

    protected Benutzer erzeugeBenutzerInDb(String benutzername, String passwort, BenutzerStatus status,
        String... rollen) {
        Benutzer benutzer = new Benutzer();
        benutzer.setBenutzername(benutzername);

        // Setze Passwort
        String passwortHash = passwordEncoder.encode(passwort);
        benutzer.setPasswort(passwortHash);
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
