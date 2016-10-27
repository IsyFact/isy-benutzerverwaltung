package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.BenutzerAnlegen;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.PasswortAendern;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.PasswortZuruecksetzen;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.RollenTestdaten;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Testet das Ändern von Benutzern.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerAendernTest extends AbstractBenutzerverwaltungTest {

    private static final String BENUTZERNAME = "testperson";

    private static final String PASSWORT = "test#passwort";

    private static final String PASSWORT_NEU = "test#passwort-neu";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DatabaseSetup("testBenutzerverwaltungSetup.xml")
    public void testSetzePasswort() throws BenutzerverwaltungBusinessException {
        benutzerEinspielen();

        PasswortAendern passwortAendern =
            new PasswortAendern(BENUTZERNAME, PASSWORT, PASSWORT_NEU, PASSWORT_NEU);
        BenutzerDaten benutzerDaten = benutzerverwaltung.setzePasswort(passwortAendern);

        assertTrue(passwordEncoder.matches(PASSWORT_NEU, benutzerDaten.getPasswort()));
    }

    @Test
    @DatabaseSetup("testBenutzerverwaltungSetup.xml")
    public void testSetzePasswortZurück() throws BenutzerverwaltungBusinessException {
        benutzerEinspielen();

        PasswortZuruecksetzen passwortZuruecksetzen =
            new PasswortZuruecksetzen(BENUTZERNAME, PASSWORT_NEU, PASSWORT_NEU);
        BenutzerDaten benutzerDaten = benutzerverwaltung.setzePasswortZurueck(passwortZuruecksetzen);

        assertTrue(passwordEncoder.matches(PASSWORT_NEU, benutzerDaten.getPasswort()));
    }

    @Test
    @DatabaseSetup("testBenutzerverwaltungSetup.xml")
    @ExpectedDatabase(value = "testBenutzerverwaltungRolleZugewiesen.xml",
                      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testWeiseBenutzerRollenZu() throws BenutzerverwaltungBusinessException {
        List<String> benutzernamen = Arrays.asList("benutzer", "admin");
        RolleDaten rolle =
            new RolleDaten(RollenTestdaten.ROLLE_MODERATOR_ID, RollenTestdaten.ROLLE_MODERATOR_NAME);

        benutzerverwaltung.weiseRolleZu(rolle, benutzernamen);
    }

    @Test
    @DatabaseSetup("testBenutzerverwaltungSetup.xml")
    @ExpectedDatabase(value = "testBenutzerverwaltungRolleEntzogen.xml",
                      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testEntzieheBenutzerRolle() throws BenutzerverwaltungBusinessException {
        List<String> benutzernamen = Arrays.asList("benutzer", "admin");
        RolleDaten rolle =
            new RolleDaten(RollenTestdaten.ROLLE_MITGLIED_ID, RollenTestdaten.ROLLE_MITGLIED_NAME);

        benutzerverwaltung.entzieheRolle(rolle, benutzernamen);
    }

    private void benutzerEinspielen() {
        BenutzerAnlegen benutzer = new BenutzerAnlegen();
        benutzer.setBenutzername(BENUTZERNAME);
        benutzer.setPasswort(PASSWORT);
        benutzer.setStatus(BenutzerStatus.AKTIVIERT);
        benutzer.getRollen()
            .add(new RolleDaten(RollenTestdaten.ROLLE_MITGLIED_ID, RollenTestdaten.ROLLE_MITGLIED_NAME));
        try {
            benutzerverwaltung.legeBenutzerAn(benutzer);
        } catch (BenutzerverwaltungValidationException e) {
            fail(e.getMessage());
        }
    }
}
