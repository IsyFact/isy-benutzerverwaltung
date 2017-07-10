package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.passwortzuruecksetzen;

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


import de.bund.bva.isyfact.benutzerverwaltung.gui.common.daten.PasswortZuruecksetzenDaten;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;
import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;

/**
 * Model zum Zurücksetzen von Passwörtern.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class PasswortZuruecksetzenModel extends AbstractMaskenModel {

    private static final long serialVersionUID = -6110443602902438569L;

    private BenutzerModel benutzer;

    private PasswortZuruecksetzenDaten passwortZuruecksetzen = new PasswortZuruecksetzenDaten();

    /**
     * This method gets the field <tt>benutzer</tt>.
     *
     * @return the field benutzer
     */
    public BenutzerModel getBenutzer() {
        return benutzer;
    }

    /**
     * Setzt das Model des Benutzers. Bereitet das Model für die Anzeige des Benutzers vor, indem es Felder
     * vor Auswahllisten o.ä. füllt.
     *
     * @param benutzer Model des Benutzers
     */
    public void setBenutzer(BenutzerModel benutzer) {
        this.benutzer = benutzer;
        passwortZuruecksetzen.setBenutzername(benutzer.getBenutzername());
    }

    public PasswortZuruecksetzenDaten getPasswortZuruecksetzen() {
        return passwortZuruecksetzen;
    }

}
