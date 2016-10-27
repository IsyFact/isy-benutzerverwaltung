package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung;

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
