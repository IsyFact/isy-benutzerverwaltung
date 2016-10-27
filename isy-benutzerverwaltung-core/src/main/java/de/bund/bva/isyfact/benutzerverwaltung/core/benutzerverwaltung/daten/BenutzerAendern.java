package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten;

import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation.BekannterBenutzername;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation.UnbekannterBenutzername;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation.ValideRollen;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Eingabedaten für eine Änderung des Benutzers durch einen Administrator.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerAendern {

    private final String alterBenutzername;

    private final String neuerBenutzername;

    private final BenutzerStatus status;

    private final List<RolleDaten> rollen;

    private final String nachname;

    private final String vorname;

    private final String behoerde;

    private final String emailAdresse;

    private final String telefonnummer;

    private final String bemerkung;

    public BenutzerAendern(String alterBenutzername, String neuerBenutzername, BenutzerStatus status,
        List<RolleDaten> rollen, String nachname, String vorname, String behoerde, String emailAdresse,
        String telefonnummer, String bemerkung) {
        this.alterBenutzername = alterBenutzername;
        this.neuerBenutzername = neuerBenutzername;
        this.status = status;
        this.rollen = rollen;
        this.nachname = nachname;
        this.vorname = vorname;
        this.behoerde = behoerde;
        this.emailAdresse = emailAdresse;
        this.telefonnummer = telefonnummer;
        this.bemerkung = bemerkung;
    }

    @BekannterBenutzername
    public String getAlterBenutzername() {
        return alterBenutzername;
    }

    @UnbekannterBenutzername
    public String getNeuerBenutzername() {
        return neuerBenutzername;
    }

    @NotNull
    public BenutzerStatus getStatus() {
        return status;
    }

    @ValideRollen
    @Size(min = 1, message = "{validation.beanmanuell.benutzerbo.rolle.nichtAusgewaehlt}")
    public List<RolleDaten> getRollen() {
        return rollen;
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

    public String getBemerkung() {
        return bemerkung;
    }
}
