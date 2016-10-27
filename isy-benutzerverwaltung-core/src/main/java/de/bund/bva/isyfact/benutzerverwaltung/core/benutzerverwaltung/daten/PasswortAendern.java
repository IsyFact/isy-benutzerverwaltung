package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten;

import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation.PasswortKorrekt;

import javax.validation.constraints.NotNull;

/**
 * Eingabedaten für eine Änderung des Passworts durch den Benutzer selbst.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
@PasswortKorrekt
public class PasswortAendern extends PasswortZuruecksetzen {

    @NotNull
    private final String altesPasswort;

    /**
     * Erzeugt eine Instanz der Eingabedaten.
     *
     * @param benutzername              Benutzername
     * @param altesPasswort             altes Passwort
     * @param neuesPasswort             neues Passwort
     * @param neuesPasswortBestaetigung Bestätigung des neuen Passworts
     */
    public PasswortAendern(String benutzername, String altesPasswort, String neuesPasswort,
        String neuesPasswortBestaetigung) {
        super(benutzername, neuesPasswort, neuesPasswortBestaetigung);
        this.altesPasswort = altesPasswort;
    }

    @NotNull
    public String getAltesPasswort() {
        return altesPasswort;
    }

}
