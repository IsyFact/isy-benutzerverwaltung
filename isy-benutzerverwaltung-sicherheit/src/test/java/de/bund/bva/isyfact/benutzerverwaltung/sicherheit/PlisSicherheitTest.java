package de.bund.bva.isyfact.benutzerverwaltung.sicherheit;

import de.bund.bva.isyfact.benutzerverwaltung.AbstractFfBenutzerzeichnisTest;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Benutzer;
import de.bund.bva.isyfact.benutzerverwaltung.sicherheit.exception.BenutzerverwaltungAuthentifizierungFehlgeschlagenException;
import de.bund.bva.pliscommon.sicherheit.Berechtigungsmanager;
import de.bund.bva.pliscommon.sicherheit.Sicherheit;
import de.bund.bva.pliscommon.sicherheit.common.exception.AuthentifizierungFehlgeschlagenException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Tests, welche die erwartete Funktionalität der PLIS-Sicherheit prüfen
 * 
 * @author <a href="jonas.zitz@capgemini.com">Jonas Zitz</a>
 * @version $Revision: 41699 $
 */
public class PlisSicherheitTest extends AbstractFfBenutzerzeichnisTest {

    @Autowired
    Sicherheit<BenutzerverwaltungAufrufKontextImpl> sicherheit;

    @Test
    public void testAuthentifizierungErfolgreichMitHash()
        throws BenutzerverwaltungAuthentifizierungFehlgeschlagenException {
        Benutzer bDb = erzeugeBenutzerInDb("Benutzer_1", "geheim", BenutzerStatus.AKTIVIERT, ROLLE_B);
        BenutzerverwaltungAufrufKontextImpl unauthentifizierterAufrufKontext =
            new BenutzerverwaltungAufrufKontextImpl();
        unauthentifizierterAufrufKontext.setDurchfuehrenderBenutzerKennung("Benutzer_1");
        unauthentifizierterAufrufKontext.setDurchfuehrenderBenutzerPasswort(bDb.getPasswort());
        unauthentifizierterAufrufKontext.setPasswortIstHash(true);
        unauthentifizierterAufrufKontext.setKorrelationsId("DUMMY-ID");

        // Authentifizierung mit Klartext-Passwort
        Berechtigungsmanager bm =
            sicherheit.getBerechtigungsManagerUndAuthentifiziere(unauthentifizierterAufrufKontext);
        assertNotNull(bm);
        assertFalse(bm.hatRecht(RECHT_A));
        assertTrue(bm.hatRecht(RECHT_B));
    }

    @Test(expected = AuthentifizierungFehlgeschlagenException.class)
    public void testAuthentifizierungFehlgehlschagenMitHash()
        throws BenutzerverwaltungAuthentifizierungFehlgeschlagenException {
        Benutzer bDb = erzeugeBenutzerInDb("Benutzer_1", "geheim", BenutzerStatus.AKTIVIERT);
        BenutzerverwaltungAufrufKontextImpl unauthentifizierterAufrufKontext =
            new BenutzerverwaltungAufrufKontextImpl();
        unauthentifizierterAufrufKontext.setDurchfuehrenderBenutzerKennung(bDb.getBenutzername());
        unauthentifizierterAufrufKontext.setDurchfuehrenderBenutzerPasswort("falsch");
        unauthentifizierterAufrufKontext.setPasswortIstHash(true);
        unauthentifizierterAufrufKontext.setKorrelationsId("DUMMY-ID");

        // Authentifizierung mit Klartext-Passwort
        Berechtigungsmanager bm =
            sicherheit.getBerechtigungsManagerUndAuthentifiziere(unauthentifizierterAufrufKontext);
        assertNull(bm);
    }

    @Test
    public void testAuthentifizierungErfolgreichOhneHash()
        throws BenutzerverwaltungAuthentifizierungFehlgeschlagenException {
        erzeugeBenutzerInDb("Benutzer_1", "geheim", BenutzerStatus.AKTIVIERT, ROLLE_A, ROLLE_B);
        BenutzerverwaltungAufrufKontextImpl unauthentifizierterAufrufKontext =
            new BenutzerverwaltungAufrufKontextImpl();
        unauthentifizierterAufrufKontext.setDurchfuehrenderBenutzerKennung("Benutzer_1");
        unauthentifizierterAufrufKontext.setDurchfuehrenderBenutzerPasswort("geheim");
        unauthentifizierterAufrufKontext.setPasswortIstHash(false);
        unauthentifizierterAufrufKontext.setKorrelationsId("DUMMY-ID");

        // Authentifizierung mit Klartext-Passwort
        Berechtigungsmanager bm =
            sicherheit.getBerechtigungsManagerUndAuthentifiziere(unauthentifizierterAufrufKontext);
        assertNotNull(bm);
        assertTrue(bm.hatRecht(RECHT_A));
        assertTrue(bm.hatRecht(RECHT_B));
    }

    @Test(expected = AuthentifizierungFehlgeschlagenException.class)
    public void testAuthentifizierungFehlgehlschagenOhneHash()
        throws BenutzerverwaltungAuthentifizierungFehlgeschlagenException {
        erzeugeBenutzerInDb("Benutzer_1", "geheim", BenutzerStatus.AKTIVIERT);
        BenutzerverwaltungAufrufKontextImpl unauthentifizierterAufrufKontext =
            new BenutzerverwaltungAufrufKontextImpl();
        unauthentifizierterAufrufKontext.setDurchfuehrenderBenutzerKennung("Benutzer_1");
        unauthentifizierterAufrufKontext.setDurchfuehrenderBenutzerPasswort("falschesPasswort");
        unauthentifizierterAufrufKontext.setPasswortIstHash(true);
        unauthentifizierterAufrufKontext.setKorrelationsId("DUMMY-ID");

        // Authentifizierung mit Klartext-Passwort
        Berechtigungsmanager bm =
            sicherheit.getBerechtigungsManagerUndAuthentifiziere(unauthentifizierterAufrufKontext);
        assertNull(bm);
    }
}
