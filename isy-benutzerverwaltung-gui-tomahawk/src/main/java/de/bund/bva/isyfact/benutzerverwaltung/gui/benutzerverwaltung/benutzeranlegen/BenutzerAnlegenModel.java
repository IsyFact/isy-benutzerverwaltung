package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzeranlegen;

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

import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.daten.BenutzerAnlegenDaten;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.RolleModel;
import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;

import java.util.Arrays;
import java.util.List;

/**
 * Model zum Anlegen von Benutzern.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerAnlegenModel extends AbstractMaskenModel {
    private static final long serialVersionUID = -6110443602902438569L;

    /**
     * Liste aller {@link BenutzerStatus} zur Anzeige in einer Auswahlliste.
     */
    private final List<BenutzerStatus> alleBenutzerStatus = Arrays.asList(BenutzerStatus.values());

    private BenutzerAnlegenDaten benutzer = new BenutzerAnlegenDaten();

    /**
     * Liste aller Rollen zur Anzeige in einer Auswahlliste.
     */
    private List<RolleModel> alleRollen;

    public List<BenutzerStatus> getAlleBenutzerStatus() {
        return alleBenutzerStatus;
    }

    public List<RolleModel> getAlleRollen() {
        return alleRollen;
    }

    public void setAlleRollen(List<RolleModel> alleRollen) {
        this.alleRollen = alleRollen;
    }

    public BenutzerAnlegenDaten getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(BenutzerAnlegenDaten benutzer) {
        this.benutzer = benutzer;
    }

}
