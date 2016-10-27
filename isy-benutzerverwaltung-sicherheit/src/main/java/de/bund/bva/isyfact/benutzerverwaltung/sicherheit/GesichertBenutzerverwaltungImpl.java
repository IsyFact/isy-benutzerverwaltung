package de.bund.bva.isyfact.benutzerverwaltung.sicherheit;

import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Suchergebnis;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSuchkriterien;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.Benutzerverwaltung;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.*;
import de.bund.bva.isyfact.benutzerverwaltung.sicherheit.konstanten.BenutzerverwaltungRecht;
import de.bund.bva.pliscommon.aufrufkontext.AufrufKontextVerwalter;
import de.bund.bva.pliscommon.sicherheit.annotation.Gesichert;

import java.util.List;

/**
 * Implementierung der Benutzerverwaltung, die alle Aufrufe mit den folgenden Rechten absichert:
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class GesichertBenutzerverwaltungImpl implements GesichertBenutzerverwaltung {

    private final Benutzerverwaltung benutzerverwaltung;

    private final AufrufKontextVerwalter<?> aufrufKontextVerwalter;

    public GesichertBenutzerverwaltungImpl(Benutzerverwaltung benutzerverwaltung,
        AufrufKontextVerwalter<?> aufrufKontextVerwalter) {
        this.benutzerverwaltung = benutzerverwaltung;
        this.aufrufKontextVerwalter = aufrufKontextVerwalter;
    }

    @Override
    public BenutzerDaten leseBenutzer(String benutzername) throws BenutzerverwaltungBusinessException {
        return benutzerverwaltung.leseBenutzer(benutzername);
    }

    @Override
    @Gesichert(BenutzerverwaltungRecht.BENUTZER_SUCHEN)
    public Suchergebnis<BenutzerDaten> sucheBenutzer(BenutzerSuchkriterien suchkriterien,
        Sortierung sortierung, Paginierung paginierung) throws BenutzerverwaltungValidationException {
        return benutzerverwaltung.sucheBenutzer(suchkriterien, sortierung, paginierung);
    }

    @Override
    @Gesichert(BenutzerverwaltungRecht.BENUTZER_ANLEGEN)
    public BenutzerDaten legeBenutzerAn(BenutzerAnlegen benutzerAnlegen)
        throws BenutzerverwaltungValidationException {
        return benutzerverwaltung.legeBenutzerAn(benutzerAnlegen);
    }

    @Override
    @Gesichert(BenutzerverwaltungRecht.BENUTZER_AENDERN_SELBST)
    public BenutzerDaten setzePasswort(PasswortAendern passwortAendern)
        throws BenutzerverwaltungBusinessException {
        return benutzerverwaltung.setzePasswort(passwortAendern);
    }

    @Override
    @Gesichert(BenutzerverwaltungRecht.BENUTZER_AENDERN)
    public BenutzerDaten setzePasswortZurueck(PasswortZuruecksetzen passwortZuruecksetzen)
        throws BenutzerverwaltungBusinessException {
        return benutzerverwaltung.setzePasswortZurueck(passwortZuruecksetzen);
    }

    @Override
    @Gesichert(BenutzerverwaltungRecht.BENUTZER_AENDERN)
    public BenutzerDaten setzeStatus(String benutzername, BenutzerStatus neuerStatus)
        throws BenutzerverwaltungBusinessException {
        return benutzerverwaltung.setzeStatus(benutzername, neuerStatus);
    }

    @Override
    @Gesichert(BenutzerverwaltungRecht.BENUTZER_AENDERN)
    public BenutzerDaten aendereBenutzer(BenutzerAendern benutzerAendern)
        throws BenutzerverwaltungBusinessException {
        return benutzerverwaltung.aendereBenutzer(benutzerAendern);
    }

    @Override
    @Gesichert(BenutzerverwaltungRecht.BENUTZER_AENDERN_SELBST)
    public BenutzerDaten aendereBenutzerSelbst(BenutzerSelbstAendern benutzerSelbstAendern)
        throws BenutzerverwaltungBusinessException {
        return benutzerverwaltung.aendereBenutzerSelbst(benutzerSelbstAendern);
    }

    @Override
    public BenutzerDaten speichereErfolgreicheAnmeldung(String benutzername)
        throws BenutzerverwaltungBusinessException {
        return benutzerverwaltung.speichereErfolgreicheAnmeldung(benutzername);
    }

    @Override
    public BenutzerDaten speichereFehlgeschlageneAnmeldung(String benutzername)
        throws BenutzerverwaltungBusinessException {
        return benutzerverwaltung.speichereFehlgeschlageneAnmeldung(benutzername);
    }

    @Override
    public BenutzerDaten speichereAbmeldung(String benutzername) throws BenutzerverwaltungBusinessException {
        return benutzerverwaltung.speichereAbmeldung(benutzername);
    }

    @Override
    @Gesichert(BenutzerverwaltungRecht.BENUTZER_LOESCHEN)
    public void loescheBenutzer(String benutzername) throws BenutzerverwaltungBusinessException {
        benutzerverwaltung.loescheBenutzer(benutzername);
    }

    @Override
    @Gesichert(BenutzerverwaltungRecht.BENUTZER_AENDERN)
    public void weiseRolleZu(RolleDaten rolle, List<String> benutzernamen)
        throws BenutzerverwaltungBusinessException {
        benutzerverwaltung.weiseRolleZu(rolle, benutzernamen);
    }

    @Override
    @Gesichert(BenutzerverwaltungRecht.BENUTZER_AENDERN)
    public void entzieheRolle(RolleDaten rolle, List<String> benutzernamen)
        throws BenutzerverwaltungBusinessException {
        benutzerverwaltung.entzieheRolle(rolle, benutzernamen);
    }
}