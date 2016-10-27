package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten;

import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation.BekannterBenutzername;

import javax.validation.constraints.NotNull;

/**
 * Eingabedaten für eine Änderung des Benutzers durch sich selbst.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerSelbstAendern {

    private final String benutzername;

    private final String nachname;

    private final String vorname;

    private final String behoerde;

    private final String emailAdresse;

    private final String telefonnummer;

    public BenutzerSelbstAendern(String benutzername, String nachname, String vorname, String behoerde,
        String emailAdresse, String telefonnummer) {
        this.benutzername = benutzername;
        this.nachname = nachname;
        this.vorname = vorname;
        this.behoerde = behoerde;
        this.emailAdresse = emailAdresse;
        this.telefonnummer = telefonnummer;
    }

    @BekannterBenutzername
    @NotNull
    public String getBenutzername() {
        return benutzername;
    }

    public String getNachname() {
        return nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public String getBehoerde() {
        return behoerde;
    }

    public String getEmailAdresse() {
        return emailAdresse;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }
}
