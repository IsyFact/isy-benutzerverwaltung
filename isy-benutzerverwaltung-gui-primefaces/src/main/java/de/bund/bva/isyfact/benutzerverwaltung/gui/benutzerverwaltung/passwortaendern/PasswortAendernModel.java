package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.passwortaendern;

import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;

/**
 * Model zum Ändern von Passwörtern.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class PasswortAendernModel extends AbstractMaskenModel {
    private static final long serialVersionUID = -6110443602902438569L;

    private String benutzername;

    private String altesPasswort;

    private String neuesPasswort;

    private String neuesPasswortBestaetigung;

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getAltesPasswort() {
        return altesPasswort;
    }

    public void setAltesPasswort(String altesPasswort) {
        this.altesPasswort = altesPasswort;
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
