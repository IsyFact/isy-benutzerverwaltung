package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten;

import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation.BekannterBenutzername;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation.PasswoerterStimmenUeberein;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Eingabedaten für eine Zurücksetzung des Passworts durch einen Administrator.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
@PasswoerterStimmenUeberein
public class PasswortZuruecksetzen {

    private String benutzername;

    private String neuesPasswort;

    private String neuesPasswortBestaetigung;

    /**
     * Erzeugt eine Instanz der Eingabedaten.
     *
     * @param benutzername              Benutzername
     * @param neuesPasswort             neues Passwort
     * @param neuesPasswortBestaetigung Bestätigung des neuen Passworts
     */
    public PasswortZuruecksetzen(String benutzername, String neuesPasswort,
        String neuesPasswortBestaetigung) {
        this.benutzername = benutzername;
        this.neuesPasswort = neuesPasswort;
        this.neuesPasswortBestaetigung = neuesPasswortBestaetigung;
    }

    @BekannterBenutzername
    @NotNull
    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    @Size(min = 8, message = "{validation.benutzer.passwort.kurz}")
    @NotNull
    public String getNeuesPasswort() {
        return neuesPasswort;
    }

    public void setNeuesPasswort(String neuesPasswort) {
        this.neuesPasswort = neuesPasswort;
    }

    public String getNeuesPasswortBestaetigung() {
        return neuesPasswortBestaetigung;
    }

    public void setNeuesPasswortBestaetigung(String neuesPasswortBestaetigung) {
        this.neuesPasswortBestaetigung = neuesPasswortBestaetigung;
    }
}
