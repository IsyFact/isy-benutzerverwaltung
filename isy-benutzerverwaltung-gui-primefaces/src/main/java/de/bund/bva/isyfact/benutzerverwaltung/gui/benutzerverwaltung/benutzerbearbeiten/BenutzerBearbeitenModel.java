package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzerbearbeiten;

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


import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.model.BenutzerKontaktdatenModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.RolleModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Model zum Bearbeiten von Benutzern.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerBearbeitenModel extends BenutzerKontaktdatenModel {
    private static final long serialVersionUID = -6110443602902438569L;

    /**
     * Liste aller {@link BenutzerStatus} zur Anzeige in einer Auswahlliste.
     */
    private final List<BenutzerStatus> alleBenutzerStatus = Arrays.asList(BenutzerStatus.values());

    private String alterBenutzername;

    private String neuerBenutzername;

    private BenutzerStatus selektierterBenutzerStatus;

    /**
     * Liste aller Rollen zur Anzeige in einer Auswahlliste.
     */
    private List<RolleModel> alleRollen;

    /**
     * Selektierte Rollen, zur Anzeige der ausgewählten Rollen in der Auswahlliste.
     */
    private List<String> selektierteRollenIds = new ArrayList<>();

    private String bemerkung;

    /**
     * Setzt das Model des Benutzers. Bereitet das Model für die Anzeige des Benutzers vor, indem es Felder
     * vor Auswahllisten o.ä. füllt.
     *
     * @param benutzer Model des Benutzers
     */
    public void setBenutzer(BenutzerModel benutzer) {
        super.setBenutzer(benutzer);

        alterBenutzername = benutzer.getBenutzername();
        neuerBenutzername = benutzer.getBenutzername();

        selektierterBenutzerStatus = benutzer.getStatus();

        // Selektierte Rollen initialisieren
        selektierteRollenIds.clear();
        if (benutzer.getRollen() != null) {
            selektierteRollenIds.addAll(
                benutzer.getRollen().stream().map(RolleModel::getRollenId).collect(Collectors.toList()));
        }

        bemerkung = benutzer.getBemerkung();
    }

    public String getAlterBenutzername() {
        return alterBenutzername;
    }

    public String getNeuerBenutzername() {
        return neuerBenutzername;
    }

    public void setNeuerBenutzername(String neuerBenutzername) {
        this.neuerBenutzername = neuerBenutzername;
    }

    public List<BenutzerStatus> getAlleBenutzerStatus() {
        return alleBenutzerStatus;
    }

    public BenutzerStatus getSelektierterBenutzerStatus() {
        return selektierterBenutzerStatus;
    }

    public void setSelektierterBenutzerStatus(BenutzerStatus selektierterBenutzerStatus) {
        this.selektierterBenutzerStatus = selektierterBenutzerStatus;
    }

    public List<RolleModel> getAlleRollen() {
        return alleRollen;
    }

    public void setAlleRollen(List<RolleModel> alleRollen) {
        this.alleRollen = alleRollen;
    }

    public List<String> getSelektierteRollenIds() {
        return selektierteRollenIds;
    }

    public void setSelektierteRollenIds(List<String> selektierteRollenIds) {
        this.selektierteRollenIds = selektierteRollenIds;
    }

    public List<RolleModel> getSelektierteRollen() {
        return alleRollen.stream().filter(rolle -> selektierteRollenIds.contains(rolle.getRollenId()))
            .collect(Collectors.toList());
    }

    public String getBemerkung() {
        return bemerkung;
    }

    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

}
