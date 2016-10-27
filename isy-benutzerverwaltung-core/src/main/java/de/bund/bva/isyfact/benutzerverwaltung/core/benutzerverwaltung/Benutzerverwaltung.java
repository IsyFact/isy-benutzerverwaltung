package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung;

import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Suchergebnis;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.*;

import java.util.List;

/**
 * Interface fuer die Benutzerverwaltung-Komponente. Diese Komponente bietet die Moeglichkeit zur Verwaltung
 * (Auslesen, Erstellen, Bearbeiten, Loeschen) von {@link BenutzerDaten Benutzern}.
 *
 * @author Capgemini, Jonas Zitz
 * @version $Id: Benutzerverwaltung.java 41878 2013-07-26 10:31:50Z jozitz $
 */
public interface Benutzerverwaltung {

    /**
     * Liest einen Benutzer anhand des Benutzernamens aus.
     *
     * @param benutzername Benutzername
     * @return die Daten des Benutzers, falls vorhanden, oder {@code null}.
     * @throws BenutzerverwaltungBusinessException falls der Benutzer nicht existiert.
     */
    BenutzerDaten leseBenutzer(String benutzername) throws BenutzerverwaltungBusinessException;

    /**
     * Sucht Benutzer anhand von {@link BenutzerSuchkriterien Suchkriterien} und sortiert die Treffer
     * anschließend nach einem {@link BenutzerSortierattribut Sortierattribut}.
     *
     * @param suchkriterien enthält die Suchkriterien
     * @param sortierung    enthält das Attribut und die Reihenfolge der Sortierung (aufsteigend, absteigend)
     * @param paginierung   enthält den ersten Treffer und die maximale Größe der Trefferliste
     * @return das Suchergebnis.
     * @throws BenutzerverwaltungValidationException falls die Eingabedaten ungültig sind.
     */
    Suchergebnis<BenutzerDaten> sucheBenutzer(BenutzerSuchkriterien suchkriterien, Sortierung sortierung,
        Paginierung paginierung) throws BenutzerverwaltungValidationException;

    /**
     * Legt einen neuen Benutzer an.
     *
     * @param benutzerAnlegen Daten des neuen Benutzers
     * @return den neuen Benutzer.
     * @throws BenutzerverwaltungValidationException falls die Eingabedaten ungültig sind.
     */
    BenutzerDaten legeBenutzerAn(BenutzerAnlegen benutzerAnlegen)
        throws BenutzerverwaltungValidationException;

    /**
     * Ändert das Passwort des Benutzers. Diese Aktion kann von Benutzern selbst durchgeführt werden.
     *
     * @param passwortAendern Eingabedaten zur Änderung des Passworts
     * @return den aktualisierten Benutzer.
     * @throws BenutzerverwaltungBusinessException falls die Eingabedaten ungültig sind.
     */
    BenutzerDaten setzePasswort(PasswortAendern passwortAendern) throws BenutzerverwaltungBusinessException;

    /**
     * Setzt das Passwort eines Benutzers zurück. Diese Aktion kann nur von einem Administrator durchgeführt
     * werden.
     *
     * @param passwortZuruecksetzen Eingabedaten zum Zurücksetzen eines Passworts
     * @return den aktualisierten Benutzer.
     * @throws BenutzerverwaltungBusinessException falls die Eingabedaten ungültig sind.
     */
    BenutzerDaten setzePasswortZurueck(PasswortZuruecksetzen passwortZuruecksetzen)
        throws BenutzerverwaltungBusinessException;

    /**
     * Setzt den Status einen Benutzers.
     *
     * @param benutzername Benutzername
     * @param neuerStatus  neuer Status
     * @return den aktualisierten Benutzer.
     * @throws BenutzerverwaltungBusinessException falls der Benutzer nicht existiert.
     */
    BenutzerDaten setzeStatus(String benutzername, BenutzerStatus neuerStatus)
        throws BenutzerverwaltungBusinessException;

    /**
     * Ändert die Daten eines Benutzers durch einen Administrator.
     *
     * @param benutzerAendern neue Benutzerdaten
     * @return den aktualisierten Benutzer.
     * @throws BenutzerverwaltungBusinessException falls die Eingabedaten ungültig sind.
     */
    BenutzerDaten aendereBenutzer(BenutzerAendern benutzerAendern) throws BenutzerverwaltungBusinessException;

    /**
     * Ändert die Daten eines Benutzers durch ihn selbst.
     *
     * @param benutzerSelbstAendern neue Benutzerdaten
     * @return den aktualisierten Benutzer.
     * @throws BenutzerverwaltungBusinessException falls die Eingabedaten ungültig sind.
     */
    BenutzerDaten aendereBenutzerSelbst(BenutzerSelbstAendern benutzerSelbstAendern)
        throws BenutzerverwaltungBusinessException;

    /**
     * Löscht einen Benutzer anhand des Benutzernamens.
     *
     * @param benutzername Benutzername
     * @throws BenutzerverwaltungBusinessException falls die Eingabedaten ungültig sind.
     */
    void loescheBenutzer(String benutzername) throws BenutzerverwaltungBusinessException;

    /**
     * Speichert eine erfolgreiche Anmeldung des Benutzers.
     *
     * @param benutzername Benutzername
     * @return den aktualisierten Benutzer.
     * @throws BenutzerverwaltungBusinessException falls der Benutzer nicht existiert.
     */
    BenutzerDaten speichereErfolgreicheAnmeldung(String benutzername)
        throws BenutzerverwaltungBusinessException;

    /**
     * Speichert eine fehlgeschlagene Anmeldung des Benutzers.
     *
     * @param benutzername Benutzername
     * @return den aktualisierten Benutzer.
     * @throws BenutzerverwaltungBusinessException falls der Benutzer nicht existiert.
     */
    BenutzerDaten speichereFehlgeschlageneAnmeldung(String benutzername)
        throws BenutzerverwaltungBusinessException;

    /**
     * Speichert die Abmeldung des Benutzers.
     *
     * @param benutzername Benutzername
     * @return den aktualisierten Benutzer.
     * @throws BenutzerverwaltungBusinessException falls der Benutzer nicht existiert.
     */
    BenutzerDaten speichereAbmeldung(String benutzername) throws BenutzerverwaltungBusinessException;

    /**
     * Weist einer Menge von Benutzern eine Rolle zu.
     *
     * @param rolle         Rolle
     * @param benutzernamen Benutzer, welche die Rolle erhalten sollen
     * @throws BenutzerverwaltungBusinessException falls die Rolle oder einer der Benutzer nicht existiert.
     */
    void weiseRolleZu(RolleDaten rolle, List<String> benutzernamen)
        throws BenutzerverwaltungBusinessException;

    /**
     * Entzieht einer Menge von Benutzern eine Rolle.
     *
     * @param rolle         Rolle
     * @param benutzernamen Benutzer, welchen die Rolle entzogen werden soll.
     * @throws BenutzerverwaltungBusinessException falls die Rolle oder einer der Benutzer nicht existiert.
     */
    void entzieheRolle(RolleDaten rolle, List<String> benutzernamen)
        throws BenutzerverwaltungBusinessException;

}
