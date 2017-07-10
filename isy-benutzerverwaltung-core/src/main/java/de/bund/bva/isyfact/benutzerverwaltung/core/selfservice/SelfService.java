package de.bund.bva.isyfact.benutzerverwaltung.core.selfservice;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.PasswortZuruecksetzen;

/**
 * Interface für die SelfService Komponente.
 *
 * Mit dem SelfService kann der Benutzer bestimmte Aktionen auch ohne Admin-Berechtigung durchführen:
 *
 * - Das eigene Passwort ohne vorherige Anmeldung zurücksetzen
 *
 * @author Bjoern Saxe, msg systems ag
 * @author Alexander Salvanos, msg systems ag
 *
 */
public interface SelfService {

    /**
     * Sendet eine Email an den Benutzer, in der ein Link mit BenutzerToken enthalten ist.
     * Der Link mit BenutzerToken führt zu einer weiteren Ansicht, die das Zurücksetzen des Passworts ermöglicht.
     *
     * @param emailadresse die Emailadresse des Benutzers
     * @return  true, wenn die Email versandt wurde, sonst false
     * @throws BenutzerverwaltungBusinessException bei ungültiger Emailadresse
     */
    boolean sendePasswortZuruecksetzenEmail(String emailadresse) throws BenutzerverwaltungBusinessException;

    /**
     * Überprüft, ob das BenutzerToken abgelaufen oder nicht vorhanden ist.
     *
     * @param token Der String mit dem der Self Service eindeutig identifiziert wird.
     * @throws BenutzerverwaltungValidationException
     */
    String holeBenutzernameZuToken(String token) throws BenutzerverwaltungBusinessException;

    /**
     * Setzt das Passwort des Benutzers zurück
     *
     * @param passwortZuruecksetzen Die Eingabedaten für das Zurücksetzen des Passworts
     * @return die Daten des Benutzers mit dem geänderten Passwort
     * @throws BenutzerverwaltungValidationException bei Eingabedaten falsch sind
     */
    BenutzerDaten setzePasswortZurueck(PasswortZuruecksetzen passwortZuruecksetzen) throws BenutzerverwaltungBusinessException;
}
