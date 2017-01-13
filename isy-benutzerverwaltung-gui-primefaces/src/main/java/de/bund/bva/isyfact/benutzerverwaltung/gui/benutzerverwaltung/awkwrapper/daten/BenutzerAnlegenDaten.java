package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.daten;

/*-
 * #%L
 * IsyFact Benutzerverwaltung GUI mit Primefaces
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


import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Model zum Anlegen eines Benutzers.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerAnlegenDaten implements Serializable {
    private static final long serialVersionUID = 0L;

    private String benutzername;

    private String passwort;

    private BenutzerStatus status = BenutzerStatus.AKTIVIERT;

    private List<String> rollenIds = new ArrayList<>();

    private String nachname;

    private String vorname;

    private String behoerde;

    private String emailAdresse;

    private String telefonnummer;

    private String bemerkung;

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

    public BenutzerStatus getStatus() {
        return status;
    }

    public void setStatus(BenutzerStatus status) {
        this.status = status;
    }

    public List<String> getRollenIds() {
        return rollenIds;
    }

    public void setRollenIds(List<String> rollenIds) {
        this.rollenIds = rollenIds;
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

    public String getBemerkung() {
        return bemerkung;
    }

    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

}
