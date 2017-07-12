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

import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation.BekannterBenutzername;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation.PasswoerterStimmenUeberein;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Eingabedaten für eine Zurücksetzung des Passworts durch einen Administrator.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
@PasswoerterStimmenUeberein
public class PasswortZuruecksetzen {

    private String benutzername;

    private String neuesPasswort;

    private String neuesPasswortBestaetigung;

    /**
     * Erzeugt eine Instanz der Eingabedaten.
     *
     * @param benutzername              Benutzername
     * @param neuesPasswort             neues Passwort
     * @param neuesPasswortBestaetigung Bestätigung des neuen Passworts
     */
    public PasswortZuruecksetzen(String benutzername, String neuesPasswort,
        String neuesPasswortBestaetigung) {
        this.benutzername = benutzername;
        this.neuesPasswort = neuesPasswort;
        this.neuesPasswortBestaetigung = neuesPasswortBestaetigung;
    }

    @BekannterBenutzername
    @NotNull
    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    @Size(min = 8, message = "{validation.benutzer.passwort.kurz}")
    @NotNull
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswortZuruecksetzen that = (PasswortZuruecksetzen) o;
        return Objects.equals(benutzername, that.benutzername) &&
                Objects.equals(neuesPasswort, that.neuesPasswort) &&
                Objects.equals(neuesPasswortBestaetigung, that.neuesPasswortBestaetigung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(benutzername, neuesPasswort, neuesPasswortBestaetigung);
    }
}
