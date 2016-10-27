package de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungTechnicalRuntimeException;
import org.junit.Test;

/**
 * Testet das Löschen von Rollen, sowohl erfolgreich als auch mit Fehlern bei der Validierung.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class RollenLoeschenTest extends AbstractRollenverwaltungTest {

    @Test
    @DatabaseSetup("testRollenverwaltungSetup.xml")
    @ExpectedDatabase(value = "testRollenverwaltungLoeschen.xml",
                      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testLoescheRolle() throws BenutzerverwaltungBusinessException {
        rollenverwaltung.loescheRolle(RollenTestdaten.ROLLE_MODERATOR_ID);
    }

    @Test(expected = BenutzerverwaltungBusinessException.class)
    @DatabaseSetup("testRollenverwaltungSetup.xml")
    public void testLoescheRolleNichtVorhanden() throws BenutzerverwaltungBusinessException {
        rollenverwaltung.loescheRolle("gibtesnicht");
    }

    @Test(expected = BenutzerverwaltungBusinessException.class)
    @DatabaseSetup("testRollenverwaltungSetup.xml")
    public void testLoescheRolleLeererName() throws BenutzerverwaltungBusinessException {
        rollenverwaltung.loescheRolle("");
    }

    @Test(expected = BenutzerverwaltungTechnicalRuntimeException.class)
    @DatabaseSetup("testRollenverwaltungSetup.xml")
    public void testLoescheRolleNull() throws BenutzerverwaltungBusinessException {
        rollenverwaltung.loescheRolle(null);
    }

}
