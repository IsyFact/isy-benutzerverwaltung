package de.bund.bva.isyfact.benutzerverwaltung.persistence.benutzerverwaltung;

import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierrichtung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.core.TestfallKonfiguration;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSuchkriterien;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.BenutzerDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.RollenDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Benutzer;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Rolle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestfallKonfiguration.class)
@Transactional
public class BenutzerDaoTest {

    @Autowired
    protected BenutzerDao benutzerDao;

    @Autowired
    private RollenDao rollenDao;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private Benutzer erzeugeBenutzerInDb(String benutzername, String passwort, BenutzerStatus status,
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

    @Test
    public void benutzerFilterTest() {
        erzeugeBenutzerInDb("test", "geheim", BenutzerStatus.AKTIVIERT, "Rolle1", "Rolle2");
        BenutzerSuchkriterien benutzerFilter = new BenutzerSuchkriterien();
        benutzerFilter.setBenutzername("test");
        benutzerFilter.setStatus(BenutzerStatus.AKTIVIERT);

        Sortierung sortierung =
            new Sortierung(BenutzerSortierattribut.BENUTZERNAME, Sortierrichtung.ABSTEIGEND);

        Paginierung paginierung = new Paginierung(0, 100);

        List<Benutzer> benutzerListe =
            benutzerDao.sucheMitBenutzerFilter(benutzerFilter, sortierung, paginierung);

        assertEquals(1, benutzerListe.size());

    }

    @Test
    public void getBenutzerZuRollenTest() {
        Benutzer benutzer = erzeugeBenutzerInDb("DASISTEINTEST", "geheim", null, "RolleId_1");
        BenutzerSuchkriterien benutzerFilter = new BenutzerSuchkriterien();
        benutzerFilter.setRollenId("RolleId_1");
        benutzerFilter.setBenutzername("DASISTEINTEST");

        Sortierung sortierung = new Sortierung(BenutzerSortierattribut.NACHNAME, Sortierrichtung.ABSTEIGEND);
        Paginierung paginierung = new Paginierung(0, 100);

        List<Benutzer> benutzerListe =
            benutzerDao.sucheMitBenutzerFilter(benutzerFilter, sortierung, paginierung);

        assertEquals(1, benutzerListe.size());

        benutzerDao.loesche(benutzer);
        benutzerListe = benutzerDao.sucheMitBenutzerFilter(benutzerFilter, sortierung, paginierung);
        assertEquals(0, benutzerListe.size());
    }
}
