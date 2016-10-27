package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Testet das Anmelden (erfolgreich, fehlerhaft) und das Abmelden von Benutzern.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerAnmeldenTest extends AbstractBenutzerverwaltungTest {

    @Test
    @DatabaseSetup("testBenutzerverwaltungSetup.xml")
    public void testMeldeBenutzerErfolgreichAn() throws BenutzerverwaltungBusinessException {
        BenutzerDaten benutzerDaten = benutzerverwaltung.speichereErfolgreicheAnmeldung("benutzer");

        assertEquals(0, benutzerDaten.getFehlanmeldeVersuche());
        assertNotNull(benutzerDaten.getLetzteAnmeldung());
    }

    @Test
    @DatabaseSetup("testBenutzerverwaltungSetup.xml")
    @ExpectedDatabase(value = "testBenutzerverwaltungAnmeldungFehlgeschlagen.xml",
                      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testMeldeBenutzerFehlgeschlagenAn() throws BenutzerverwaltungBusinessException {
        BenutzerDaten benutzerDaten = benutzerverwaltung.leseBenutzer("benutzer");
        int fehlversuche = benutzerDaten.getFehlanmeldeVersuche();

        benutzerDaten = benutzerverwaltung.speichereFehlgeschlageneAnmeldung("benutzer");

        assertEquals(fehlversuche + 1, benutzerDaten.getFehlanmeldeVersuche());
    }

    @Test
    @DatabaseSetup("testBenutzerverwaltungSetup.xml")
    public void testMeldeBenutzerAb() throws BenutzerverwaltungBusinessException {
        BenutzerDaten benutzerDaten = benutzerverwaltung.speichereAbmeldung("benutzer");

        assertNotNull(benutzerDaten.getLetzteAbmeldung());
    }

}
