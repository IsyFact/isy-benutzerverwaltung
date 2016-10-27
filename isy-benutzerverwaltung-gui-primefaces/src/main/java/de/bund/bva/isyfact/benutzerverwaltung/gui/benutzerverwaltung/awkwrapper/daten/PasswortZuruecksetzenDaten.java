package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.daten;

import java.io.Serializable;

/**
 * Model zur Zurücksetzung des Passworts durch einen Administrator.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class PasswortZuruecksetzenDaten implements Serializable {
    private static final long serialVersionUID = 0L;

    private String benutzername;

    private String neuesPasswort;

    private String neuesPasswortBestaetigung;

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

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
