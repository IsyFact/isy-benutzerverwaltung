package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzerpasswortaendern;

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
 * Model zum Ändern des eigenen Passworts.
 *
 * @author msg systems ag, Björn Saxe
 */
public class BenutzerPasswortAendernModel extends AbstractMaskenModel {

    private static final long serialVersionUID = 1L;
    
    private String altesPasswort;
    private String neuesPasswort;
    private String neuesPasswortWiederholung;

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

    public String getNeuesPasswortWiederholung() {
        return neuesPasswortWiederholung;
    }

    public void setNeuesPasswortWiederholung(String neuesPasswortWiederholung) {
        this.neuesPasswortWiederholung = neuesPasswortWiederholung;
    }    
}
