package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzersuchen;

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
