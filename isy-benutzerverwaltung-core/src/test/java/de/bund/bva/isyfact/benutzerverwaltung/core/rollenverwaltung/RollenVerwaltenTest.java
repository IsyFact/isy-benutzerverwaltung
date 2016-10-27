package de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.daten.RolleAendern;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RollenVerwaltenTest extends AbstractRollenverwaltungTest {

    private static final String NEUE_ROLLE_ID = "TUERSTEHER";

    private static final String NEUE_ROLLE_NAME = "Administrator-Rolle mit Rechten zum Löschen von Benutzern";

    @Test
    @DatabaseSetup("testRollenverwaltungSetup.xml")
    @ExpectedDatabase(value = "testRollenverwaltungAendern.xml",
                      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testAendereRolle() throws BenutzerverwaltungValidationException {
        RolleAendern rolleAendern =
            new RolleAendern(RollenTestdaten.ROLLE_MODERATOR_ID, NEUE_ROLLE_ID, NEUE_ROLLE_NAME);

        RolleDaten rolleDaten = rollenverwaltung.aendereRolle(rolleAendern);

        assertNotNull(rolleDaten);
        assertEquals(NEUE_ROLLE_ID, rolleDaten.getRollenId());
        assertEquals(NEUE_ROLLE_NAME, rolleDaten.getRollenName());
    }

}
