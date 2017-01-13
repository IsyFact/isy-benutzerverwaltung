package de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.rollebearbeiten;

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


import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.RolleModel;
import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;

/**
 * Model zum Bearbeiten von Rollen.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class RolleBearbeitenModel extends AbstractMaskenModel {

    private static final long serialVersionUID = -6110443602902438569L;

    private RolleModel rolle;

    private String rollenIdVorVerarbeitung;

    public RolleModel getRolle() {
        return rolle;
    }

    public void setRolle(RolleModel rolle) {
        this.rolle = rolle;
    }

    public String getRollenIdVorVerarbeitung() {
        return rollenIdVorVerarbeitung;
    }

    public void setRollenIdVorVerarbeitung(String rollenIdVorVerarbeitung) {
        this.rollenIdVorVerarbeitung = rollenIdVorVerarbeitung;
    }
}
