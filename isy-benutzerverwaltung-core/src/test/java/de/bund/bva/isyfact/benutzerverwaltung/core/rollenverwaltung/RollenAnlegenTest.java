package de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungTechnicalRuntimeException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.daten.RolleAnlegen;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RollenAnlegenTest extends AbstractRollenverwaltungTest {

    private static final String NEUE_ROLLE_ID = "TUERSTEHER";

    private static final String NEUE_ROLLE_NAME = "Administrator-Rolle mit Rechten zum Löschen von Benutzern";

    @Test
    @DatabaseSetup("testRollenverwaltungSetup.xml")
    @ExpectedDatabase(value = "testRollenverwaltungSpeichern.xml",
                      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testLegeRolleAn() throws BenutzerverwaltungValidationException {
        RolleAnlegen rolleAnlegen = new RolleAnlegen(NEUE_ROLLE_ID, NEUE_ROLLE_NAME);

        RolleDaten rolleDaten = rollenverwaltung.legeRolleAn(rolleAnlegen);

        assertNotNull(rolleDaten);
        assertEquals(NEUE_ROLLE_ID, rolleDaten.getRollenId());
        assertEquals(NEUE_ROLLE_NAME, rolleDaten.getRollenName());
    }

    @Test(expected = BenutzerverwaltungValidationException.class)
    @DatabaseSetup("testRollenverwaltungSetup.xml")
    public void testLegeRolleAnIdLeer() throws BenutzerverwaltungValidationException {
        rollenverwaltung.legeRolleAn(new RolleAnlegen("", NEUE_ROLLE_NAME));
    }

    @Test(expected = BenutzerverwaltungValidationException.class)
    @DatabaseSetup("testRollenverwaltungSetup.xml")
    public void testLegeRolleAnNameLeer() throws BenutzerverwaltungValidationException {
        rollenverwaltung.legeRolleAn(new RolleAnlegen(NEUE_ROLLE_ID, ""));
    }

    @Test(expected = BenutzerverwaltungValidationException.class)
    @DatabaseSetup("testRollenverwaltungSetup.xml")
    public void testLegeRolleAnFelderNull() throws BenutzerverwaltungValidationException {
        rollenverwaltung.legeRolleAn(new RolleAnlegen(null, null));
    }

    @Test(expected = BenutzerverwaltungTechnicalRuntimeException.class)
    @DatabaseSetup("testRollenverwaltungSetup.xml")
    public void testLegeRolleAnRolleNull() throws BenutzerverwaltungValidationException {
        rollenverwaltung.legeRolleAn(null);
    }

}
