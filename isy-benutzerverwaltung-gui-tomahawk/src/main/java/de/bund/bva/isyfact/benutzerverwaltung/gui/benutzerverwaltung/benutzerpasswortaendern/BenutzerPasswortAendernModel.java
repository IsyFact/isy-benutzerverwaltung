package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzerpasswortaendern;

import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;

/**
 * Model zum Ändern des eigenen Passworts.
 *
 * @author msg systems ag, Björn Saxe
 */
public class BenutzerPasswortAendernModel extends AbstractMaskenModel {

    private static final long serialVersionUID = 1L;
    
    private String altesPasswort;
    private String neuesPasswort;
    private String neuesPasswortWiederholung;

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

    public String getNeuesPasswortWiederholung() {
        return neuesPasswortWiederholung;
    }

    public void setNeuesPasswortWiederholung(String neuesPasswortWiederholung) {
        this.neuesPasswortWiederholung = neuesPasswortWiederholung;
    }    
}
