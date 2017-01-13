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

import javax.validation.constraints.NotNull;

/**
 * Eingabedaten für eine Änderung des Benutzers durch sich selbst.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerSelbstAendern {

    private final String benutzername;

    private final String nachname;

    private final String vorname;

    private final String behoerde;

    private final String emailAdresse;

    private final String telefonnummer;

    public BenutzerSelbstAendern(String benutzername, String nachname, String vorname, String behoerde,
        String emailAdresse, String telefonnummer) {
        this.benutzername = benutzername;
        this.nachname = nachname;
        this.vorname = vorname;
        this.behoerde = behoerde;
        this.emailAdresse = emailAdresse;
        this.telefonnummer = telefonnummer;
    }

    @BekannterBenutzername
    @NotNull
    public String getBenutzername() {
        return benutzername;
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
}
