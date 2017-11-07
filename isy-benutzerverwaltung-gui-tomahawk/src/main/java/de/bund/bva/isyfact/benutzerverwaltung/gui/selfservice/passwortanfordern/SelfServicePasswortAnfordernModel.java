package de.bund.bva.isyfact.benutzerverwaltung.gui.selfservice.passwortanfordern;

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
 * Model zum Zurücksetzen von Passwörtern.
 *
 * @author msg systems ag, Bjoern Saxe
 * @author msg systems ag, Alexander Salvanos
 */
public class SelfServicePasswortAnfordernModel extends AbstractMaskenModel {

    private String email;

    private boolean passwortAngefordert;

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPasswortAngefordert() {
        return passwortAngefordert;
    }

    public void setPasswortAngefordert(boolean passwortAngefordert) {
        this.passwortAngefordert = passwortAngefordert;
    }
}
