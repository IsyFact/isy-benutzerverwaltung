package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.model;

import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;
import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;

/**
 * Model zum Bearbeiten der Kontaktdaten eines Benutzers.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerKontaktdatenModel extends AbstractMaskenModel {
    private static final long serialVersionUID = 0L;

    private String nachname;

    private String vorname;

    private String behoerde;

    private String emailAdresse;

    private String telefonnummer;

    /**
     * Bereitet das Model für die Bearbeitung des Benutzers vor.
     *
     * @param benutzer Model des Benutzers
     */
    public void setBenutzer(BenutzerModel benutzer) {
        nachname = benutzer.getNachname();
        vorname = benutzer.getVorname();
        behoerde = benutzer.getBehoerde();
        emailAdresse = benutzer.getEmailAdresse();
        telefonnummer = benutzer.getTelefonnummer();
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

    public String getEmailAdresse() {
        return emailAdresse;
    }

    public void setEmailAdresse(String emailAdresse) {
        this.emailAdresse = emailAdresse;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

}
