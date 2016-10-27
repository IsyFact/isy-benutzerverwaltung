package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzersuchen;

import java.io.Serializable;

import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;

/**
 * Diese Klasse beinhaltet die Filterkriterien zur Suche nach Benutzern.
 *
 * @author Capgemini, Simon Spielmann
 */
public class BenutzerSuchkriterienModel implements Serializable {

    /**
     * ID
     */
    private static final long serialVersionUID = 0L;

    private String nachname;

    private String vorname;

    private String behoerde;

    private String rollenId;

    private String benutzername;

    private BenutzerStatus status;

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getBehoerde() {
        return behoerde;
    }

    public void setBehoerde(String behoerde) {
        this.behoerde = behoerde;
    }

    public String getRollenId() {
        return rollenId;
    }

    public void setRollenId(String rollenId) {
        this.rollenId = rollenId;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public BenutzerStatus getStatus() {
        return status;
    }

    public void setStatus(BenutzerStatus status) {
        this.status = status;
    }
}
