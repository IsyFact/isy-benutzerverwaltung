package de.bund.bva.isyfact.benutzerverwaltung.sicherheit;

import de.bund.bva.pliscommon.sicherheit.accessmgr.AuthentifzierungErgebnis;

import java.util.ArrayList;
import java.util.List;

public class BenutzerverwaltungAuthentifizierungErgebnis implements AuthentifzierungErgebnis {

    private static final long serialVersionUID = 1L;

    private List<String> rollenIds = new ArrayList<>();

    private String benutzername = "";

    private String passwort = "";

    private String nachname = "";

    private String vorname = "";

    private String behoerde = "";

    public List<String> getRollenIds() {
        return rollenIds;
    }

    public void setRollenIds(List<String> rollenIds) {
        this.rollenIds = rollenIds;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

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

}
