package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten;

/*-
 * #%L
 * IsyFact Benutzerverwaltung Core
 * %%
 * Copyright (C) 2016 - 2017 Bundesverwaltungsamt (BVA)
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation.UnbekannterBenutzername;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation.ValideRollen;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Schnittstellen-Objekt zum Anlegen eines Benutzers.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerAnlegen implements Serializable {
    private static final long serialVersionUID = 0L;

    private String benutzername;

    private String passwort;

    private BenutzerStatus status;

    private List<RolleDaten> rollen = new ArrayList<>();

    private String nachname;

    private String vorname;

    private String behoerde;

    private String emailAdresse;

    private String telefonnummer;

    private String bemerkung;

    @UnbekannterBenutzername
    @NotNull
    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    @Size(min = 8, message = "{validation.benutzer.passwort.kurz}")
    @NotNull
    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    @NotNull
    public BenutzerStatus getStatus() {
        return status;
    }

    public void setStatus(BenutzerStatus status) {
        this.status = status;
    }

    @ValideRollen
    @Size(min = 1, message = "{validation.benutzer.rollen.leer}")
    @NotNull
    public List<RolleDaten> getRollen() {
        return rollen;
    }

    public void setRollen(List<RolleDaten> rollen) {
        this.rollen = rollen;
    }

    @Size(max = 50)
    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    @Size(max = 50)
    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    @Size(max = 50)
    public String getBehoerde() {
        return behoerde;
    }

    public void setBehoerde(String behoerde) {
        this.behoerde = behoerde;
    }

    @NotBlank
    @Email
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

    @Size(max = 500)
    public String getBemerkung() {
        return bemerkung;
    }

    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }
}
