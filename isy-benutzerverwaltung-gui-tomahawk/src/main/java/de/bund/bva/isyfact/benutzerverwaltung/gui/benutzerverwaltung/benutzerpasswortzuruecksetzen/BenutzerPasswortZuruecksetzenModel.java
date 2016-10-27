package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzerpasswortzuruecksetzen;

import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;

/**
 * Model zum Zurücksetzen des Passworts.
 *
 * @author msg systems ag, Björn Saxe
 */
public class BenutzerPasswortZuruecksetzenModel extends AbstractMaskenModel {

    private static final long serialVersionUID = 1L;
    
    private String benutzername;
    private String passwort;
    private String passwortWiederholung;

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzerName) {
        this.benutzername = benutzerName;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getPasswortWiederholung() {
        return passwortWiederholung;
    }

    public void setPasswortWiederholung(String passwortWiederholung) {
        this.passwortWiederholung = passwortWiederholung;
    }

    
}
