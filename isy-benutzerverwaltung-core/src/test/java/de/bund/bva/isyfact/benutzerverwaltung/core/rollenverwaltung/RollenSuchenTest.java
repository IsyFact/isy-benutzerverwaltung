package de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierrichtung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Suchergebnis;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;
import org.junit.Test;

import static org.junit.Assert.*;

public class RollenSuchenTest extends AbstractRollenverwaltungTest {

    private static final String NEUE_ROLLE_ID = "TUERSTEHER";

    private static final String NEUE_ROLLE_NAME = "Administrator-Rolle mit Rechten zum Löschen von Benutzern";

    @Test
    @DatabaseSetup("testRollenverwaltungSetup.xml")
    public void testLeseRolle() throws BenutzerverwaltungBusinessException {
        RolleDaten rolle = rollenverwaltung.leseRolle(RollenTestdaten.ROLLE_ADMINISTRATOR_ID);
        assertNotNull(rolle);
        assertEquals(RollenTestdaten.ROLLE_ADMINISTRATOR_ID, rolle.getRollenId());
        assertEquals(RollenTestdaten.ROLLE_ADMINISTRATOR_NAME, rolle.getRollenName());
    }

    @Test(expected = BenutzerverwaltungBusinessException.class)
    @DatabaseSetup("testRollenverwaltungSetup.xml")
    public void testLeseRolleNichtVorhanden() throws BenutzerverwaltungBusinessException {
        rollenverwaltung.leseRolle("");
    }

    @Test
    @DatabaseSetup("testRollenverwaltungSetup.xml")
    public void testSucheAlleRollen() throws BenutzerverwaltungValidationException {
        Suchergebnis<RolleDaten> rollen = rollenverwaltung.sucheRollen(new RolleSuchkriterien(),
            new Sortierung(RolleSortierattribut.getStandard(), Sortierrichtung.getStandard()),
            new Paginierung(0, 10));
        assertNotNull(rollen);
        assertTrue(rollen.getAnzahlTreffer() == 3);
        for (RolleDaten rolleDaten : rollen.getTrefferliste()) {
            assertTrue(RollenTestdaten.ROLLEN_IDS.contains(rolleDaten.getRollenId()));
            assertTrue(RollenTestdaten.ROLLEN_NAMEN.contains(rolleDaten.getRollenName()));
        }
    }

}
