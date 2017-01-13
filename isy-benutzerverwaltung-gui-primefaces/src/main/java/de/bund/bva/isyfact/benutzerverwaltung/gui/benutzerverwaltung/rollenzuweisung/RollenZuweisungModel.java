package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.rollenzuweisung;

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

import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.RolleModel;
import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Dieses Model stellt die Daten zur Rollenzuweisung-Maske bereit.
 *
 * @author Capgemini, Jonas Zitz
 * @author msg systems ag, Stefan Dellmuth
 */
public class RollenZuweisungModel extends AbstractMaskenModel {
    private static final long serialVersionUID = 1L;

    /**
     * Liste aller Rollen zur Anzeige in einer Auswahlliste.
     */
    private List<RolleModel> alleRollen;

    /**
     * Selektierte Rollen, zur Anzeige der ausgewählten Rollen in der Auswahlliste.
     */
    private String selektierteRollenId;

    /**
     * Liste aller Benutzer zur Anzeige in einer Auswahlliste.
     */
    private List<BenutzerModel> alleBenutzer = new ArrayList<>();

    /**
     * Selektierte Benutzernamen für die selektierte Rolle.
     */
    private List<String> benutzerZuRolle = new ArrayList<>();

    public List<RolleModel> getAlleRollen() {
        return alleRollen;
    }

    public void setAlleRollen(List<RolleModel> alleRollen) {
        this.alleRollen = alleRollen;
    }

    public String getSelektierteRollenId() {
        return selektierteRollenId;
    }

    public void setSelektierteRollenId(String selektierteRollenId) {
        this.selektierteRollenId = selektierteRollenId;
    }

    public List<BenutzerModel> getAlleBenutzer() {
        return alleBenutzer;
    }

    public void setAlleBenutzer(List<BenutzerModel> alleBenutzer) {
        this.alleBenutzer = alleBenutzer;
    }

    public List<String> getBenutzerZuRolle() {
        return benutzerZuRolle;
    }

    public void setBenutzerZuRolle(List<String> benutzerZuRolle) {
        this.benutzerZuRolle = benutzerZuRolle;
    }

}
