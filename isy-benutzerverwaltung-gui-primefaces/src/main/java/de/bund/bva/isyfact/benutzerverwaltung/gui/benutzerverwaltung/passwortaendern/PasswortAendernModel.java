package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.passwortaendern;

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


import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;

/**
 * Model zum Ändern von Passwörtern.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class PasswortAendernModel extends AbstractMaskenModel {
    private static final long serialVersionUID = -6110443602902438569L;

    private String benutzername;

    private String altesPasswort;

    private String neuesPasswort;

    private String neuesPasswortBestaetigung;

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

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

    public String getNeuesPasswortBestaetigung() {
        return neuesPasswortBestaetigung;
    }

    public void setNeuesPasswortBestaetigung(String neuesPasswortBestaetigung) {
        this.neuesPasswortBestaetigung = neuesPasswortBestaetigung;
    }
}
