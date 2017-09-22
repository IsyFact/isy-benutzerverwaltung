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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzersuchen.BenutzerSuchkriterienModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.RolleModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.SucheModel;

/**
 * Dieses Model stellt die Daten zur Rollenzuweisung-Maske bereit.
 *
 * @author Capgemini, Jonas Zitz
 * @author msg systems ag, Stefan Dellmuth
 */
public class RollenZuweisungModel extends SucheModel<BenutzerModel, BenutzerSuchkriterienModel> {
    private static final long serialVersionUID = 1L;

    /**
     * Liste aller Rollen zur Anzeige in einer Auswahlliste.
     */
    private List<RolleModel> alleRollen;

    /**
     * Liste aller Status zur Anzeige in einer Auswahlliste.
     */
    private final List<BenutzerStatus> alleBenutzerStatus = Arrays.asList(BenutzerStatus.values());

    /**
     * Id der ausgewählten Rolle.
     */
    private String ausgewaehlteRollenId;

    /**
     * Liste der ausgewählten Benutzer.
     */
    private List<BenutzerModel> ausgewaehlteBenutzer;

    public List<RolleModel> getAlleRollen() {
        return alleRollen;
    }

    public void setAlleRollen(List<RolleModel> alleRollen) {
        this.alleRollen = alleRollen;
    }

    public List<BenutzerStatus> getAlleBenutzerStatus() {
        return alleBenutzerStatus;
    }

    public String getAusgewaehlteRollenId() {
        return ausgewaehlteRollenId;
    }

    public void setAusgewaehlteRollenId(String ausgewaehlteRollenId) {
        this.ausgewaehlteRollenId = ausgewaehlteRollenId;
    }

    public List<BenutzerModel> getAusgewaehlteBenutzer() {
        return ausgewaehlteBenutzer;
    }

    public void setAusgewaehlteBenutzer(List<BenutzerModel> ausgewaehlteBenutzer) {
        this.ausgewaehlteBenutzer = ausgewaehlteBenutzer;
    }

    public String getRollen(BenutzerModel benutzerModel) {
        return benutzerModel.getRollen().stream().map(RolleModel::getRollenId).collect(Collectors.joining(", "));
    }
}
