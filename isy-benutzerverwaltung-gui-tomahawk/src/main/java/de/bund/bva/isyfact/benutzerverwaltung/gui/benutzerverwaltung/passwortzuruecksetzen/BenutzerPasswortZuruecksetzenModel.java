package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.passwortzuruecksetzen;

/*-
 * #%L
 * IsyFact Benutzerverwaltung GUI mit Tomahawk
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


import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;

/**
 * Model zum Zurücksetzen des Passworts.
 *
 * @author msg systems ag, Björn Saxe
 */
public class BenutzerPasswortZuruecksetzenModel extends AbstractMaskenModel {

    private static final long serialVersionUID = 1L;

    private String benutzername;

    private String passwort;

    private String passwortWiederholung;

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzerName) {
        this.benutzername = benutzerName;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getPasswortWiederholung() {
        return passwortWiederholung;
    }

    public void setPasswortWiederholung(String passwortWiederholung) {
        this.passwortWiederholung = passwortWiederholung;
    }

}
