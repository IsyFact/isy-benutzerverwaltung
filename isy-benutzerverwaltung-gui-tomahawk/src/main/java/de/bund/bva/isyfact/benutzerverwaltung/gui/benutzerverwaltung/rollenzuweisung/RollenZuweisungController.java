package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.rollenzuweisung;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzersuchen.BenutzerSuchkriterienModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.controller.AbstractSuchDataTableController;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.HinweisSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.RolleModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.SuchergebnisModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.util.AuswahlHelper;
import de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableModel.DatatableOperationMode;
import de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTablePaginationModel.PaginationType;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;

/**
 * Dieser Controller stellt die Funktionalitaet zur Rollenzuweisung-Maske
 * bereit.
 *
 * @author msg systems ag, Björn Saxe
 */
public class RollenZuweisungController extends
    AbstractSuchDataTableController<BenutzerModel, BenutzerSuchkriterienModel, RollenZuweisungModel, BenutzerSortierattribut> {

    public void filterZuruecksetzen(RollenZuweisungModel model) {
        model.setSuchkriterien(new BenutzerSuchkriterienModel());
    }

    /**
     * Diese Methode initialisiert das Suchkriterien Model. Es werden die Filter
     * und Sortierung zurückgestetzt und die Paginierung konfiguriert.
     *
     * @param model das Model.
     */
    public void initialisiereModel(RollenZuweisungModel model) {
        if (model.getSuchkriterien() == null) {
            model.setMode(DatatableOperationMode.SERVER);

            initialisiereRollen(model);

            model.setSuchkriterien(new BenutzerSuchkriterienModel());
            model.getSortModel().setProperty(BenutzerSortierattribut.NACHNAME.name());
            model.getPaginationModel().setType(PaginationType.NORMAL);
            model.getPaginationModel().setPageSize(10);
        }
    }

    private void initialisiereRollen(RollenZuweisungModel model) {

        List<RolleModel> alleRollenModel = getRollenverwaltungAwkWrapper().leseAlleRollen();

        model.setVerfuegbareRollen(AuswahlHelper.erstelleRollenDropDown(alleRollenModel));

        List<String> rollenIds = new ArrayList<>();
        for (RolleModel rolleModel : alleRollenModel) {
            rollenIds.add(rolleModel.getRollenId());
        }

        model.setVerfuegbareRollenIds(rollenIds);
        model.setAlleRollen(alleRollenModel);
    }

    /**
     * @see de.bund.bva.isyfact.benutzerverwaltung.gui.common.controller.AbstractSuchDataTableController#suche(de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.AbstractSuchDataTableModel)
     *
     * Setzt vor der Suche die Auswahl zurück.
     */
    @Override
    public void suche(RollenZuweisungModel model) {
        model.getAusgewaehlteBenutzernamen().clear();
        super.suche(model);
    }

    /**
     * @see de.bund.bva.isyfact.benutzerverwaltung.gui.common.controller.AbstractSuchDataTableController#updateDisplayItems(de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.AbstractSuchDataTableModel)
     *
     * Speichert die ausgewählten Benutzernamen vor jedem Aufruf von
     * <code>updateDisplayItems()</code>, damit diese beim Seitenwechsel
     * des Paginators erhalten bleiben. Zusätzlich wird die Rollenzuordnung
     * zur Darstellung ermittelt.
     */
    @Override
    public void updateDisplayItems(RollenZuweisungModel model) {
        speichereAusgewaehlteBenutzernamen(model);

        super.updateDisplayItems(model);

        erstelleRollenZuordnung(model);
    }

    private void speichereAusgewaehlteBenutzernamen(RollenZuweisungModel model) {
        Map<Long, Boolean> selection = model.getSelectionModel().getSelected();
        Set<String> ausgewaehlteBenutzernamen = model.getAusgewaehlteBenutzernamen();

        for (BenutzerModel benutzer : model.getDataModel().getDisplayItems()) {
            if (selection.get(benutzer.getIdentifierForItem())) {
                ausgewaehlteBenutzernamen.add(benutzer.getBenutzername());
            } else {
                ausgewaehlteBenutzernamen.remove(benutzer.getBenutzername());
            }
        }
    }

    /*
     * Erstellt eine Map, die für jeden angezeigten Benutzer (Schlüssel ist die
     * Long-ID des DataTableItems) wieder eine Map enthält, die jeder Rolle (String
     * Rollen-ID als Schlüssel) einen Boolean-Wert zuordnet, der besagt ob der
     * Benutzer die Rolle hat oder nicht.
     */
    private void erstelleRollenZuordnung(RollenZuweisungModel model) {
        Map<Long, Map<String, Boolean>> rollenZuordnung = new HashMap<>();

        for (BenutzerModel benutzer : model.getDataModel().getDisplayItems()) {
            rollenZuordnung.put(benutzer.getIdentifierForItem(), new HashMap<String, Boolean>());
            Map<String, Boolean> zuordnungBenutzer = rollenZuordnung.get(benutzer.getIdentifierForItem());
            model.getVerfuegbareRollenIds()
                .forEach(rId -> zuordnungBenutzer.put(rId, hasBenutzerRollenId(benutzer, rId)));
        }

        model.setRollenZuordnung(rollenZuordnung);
    }

    private boolean hasBenutzerRollenId(BenutzerModel benutzer, String rollenId) {
        return benutzer.getRollen().stream()
            .anyMatch(rolleModel -> rolleModel.getRollenId().equals(rollenId));
    }

    /**
     * Weist den im Model gespeicherten Benutzern die im Model gespeicherte
     * Rolle zu.
     *
     * @param model das Model.
     */
    public void rolleZuweisen(RollenZuweisungModel model) {
        speichereAusgewaehlteBenutzernamen(model);

        RolleModel rolle = getAusgewaehlteRolle(model);

        try {
            getBenutzerverwaltungAwkWrapper()
                .weiseRolleZu(rolle, new ArrayList<>(model.getAusgewaehlteBenutzernamen()));

            getMessageController().writeSuccessMessage(
                MessageSourceHolder.getMessage(HinweisSchluessel.ROLLENZUWEISUNG_BENUTZER_AKTUALISIERT));
        } catch (BenutzerverwaltungBusinessException exception) {
            zeigeNachricht(exception);
        }
    }

    /**
     * Entzieht den im Model gespeicherten Benutzern die im Model gespeicherte
     * Rolle.
     *
     * @param model das Model.
     */
    public void rolleEntziehen(RollenZuweisungModel model) {
        speichereAusgewaehlteBenutzernamen(model);

        RolleModel rolle = getAusgewaehlteRolle(model);

        try {
            getBenutzerverwaltungAwkWrapper()
                .entzieheRolle(rolle, new ArrayList<>(model.getAusgewaehlteBenutzernamen()));

            getMessageController().writeSuccessMessage(
                MessageSourceHolder.getMessage(HinweisSchluessel.ROLLENZUWEISUNG_BENUTZER_AKTUALISIERT));
        } catch (BenutzerverwaltungBusinessException exception) {
            zeigeNachricht(exception);
        }
    }

    private RolleModel getAusgewaehlteRolle(RollenZuweisungModel model) {
        return model.getAlleRollen().stream()
            .filter(rolle -> model.getRollenIdZurZuweisung().equals(rolle.getRollenId())).findFirst()
            .orElse(null);
    }

    @Override
    protected SuchergebnisModel<BenutzerModel> sucheEntitaet(BenutzerSuchkriterienModel kriterien,
        Sortierung sortierung, Paginierung paginierung) throws BenutzerverwaltungValidationException {
        return getBenutzerverwaltungAwkWrapper().sucheBenutzer(kriterien, sortierung, paginierung);
    }
}
