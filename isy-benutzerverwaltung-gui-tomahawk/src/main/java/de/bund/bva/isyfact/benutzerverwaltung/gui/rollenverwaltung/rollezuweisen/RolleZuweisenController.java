package de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.rollezuweisen;

import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzersuchen.BenutzerSuchkriterienModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.RolleModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.controller.AbstractSuchDataTableController;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.HinweisSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.SuchergebnisModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.util.AuswahlHelper;
import de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableModel.DatatableOperationMode;
import de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTablePaginationModel.PaginationType;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;

import java.util.*;

/**
 * Dieser Controller stellt die Funktionalitaet zur Rollenzuweisung-Maske
 * bereit.
 *
 * @author msg systems ag, Björn Saxe
 */
public class RolleZuweisenController extends
	AbstractSuchDataTableController<BenutzerModel, BenutzerSuchkriterienModel, RolleZuweisenModel, BenutzerSortierattribut> {

    public void filterZuruecksetzen(RolleZuweisenModel model) {
	model.setSuchkriterien(new BenutzerSuchkriterienModel());
    }

    /**
     * Diese Methode initialisiert das Suchkriterien Model. Es werden die Filter
     * und Sortierung zurückgestetzt und die Paginierung konfiguriert.
     *
     * @param model
     *            das Model.
     */
    public void initialisiereModel(RolleZuweisenModel model) {
	if (model.getSuchkriterien() == null) {
	    model.setMode(DatatableOperationMode.SERVER);

	    initialisiereRollen(model);

	    model.setSuchkriterien(new BenutzerSuchkriterienModel());
	    model.getSortModel().setProperty(BenutzerSortierattribut.NACHNAME.name());
	    model.getPaginationModel().setType(PaginationType.NORMAL);
	    model.getPaginationModel().setPageSize(10);
	}
    }

    private void initialisiereRollen(RolleZuweisenModel model) {

	List<RolleModel> alleRolleModel = getAwkWrapper().getRollen();

	model.setVerfuegbareRollen(AuswahlHelper.erstelleRollenDropDown(alleRolleModel));

	List<String> rollenIds = new ArrayList<>();
	for (RolleModel rolleModel : alleRolleModel) {
	    rollenIds.add(rolleModel.getRollenId());
	}

	model.setVerfuegbareRollenIds(rollenIds);
	model.setAlleRollen(getAwkWrapper().getRollen());
    }

    /**
     * @see de.bund.bva.isyfact.benutzerverwaltung.gui.common.controller.AbstractSuchDataTableController#suche(de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.AbstractSuchDataTableModel)
     * 
     *      Setzt vor der Suche die Auswahl zurück.
     */
    @Override
    public void suche(RolleZuweisenModel model) {
	model.getAusgewaehlteBenutzernamen().clear();
	super.suche(model);
    }

    /**
     * @see de.bund.bva.isyfact.benutzerverwaltung.gui.common.controller.AbstractSuchDataTableController#updateDisplayItems(de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.AbstractSuchDataTableModel)
     * 
     *      Speichert die ausgewählten Benutzernamen vor jedem Aufruf von
     *      <code>updateDisplayItems()</code>, damit diese beim Seitenwechsel
     *      des Paginators erhalten bleiben. Zusätzlich wird die Rollenzuordnung
     *      zur Darstellung ermittelt.
     */
    @Override
    public void updateDisplayItems(RolleZuweisenModel model) {
	speichereAusgewaehlteBenutzernamen(model);

	super.updateDisplayItems(model);

	erstelleRollenZuordnung(model);
    }

    private void speichereAusgewaehlteBenutzernamen(RolleZuweisenModel model) {
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
    private void erstelleRollenZuordnung(RolleZuweisenModel model) {
	Map<Long, Map<String, Boolean>> rollenZuordnung = new HashMap<>();

	for (BenutzerModel benutzer : model.getDataModel().getDisplayItems()) {
	    rollenZuordnung.put(benutzer.getIdentifierForItem(), new HashMap<String, Boolean>());
	    Map<String, Boolean> zuordnungBenutzer = rollenZuordnung.get(benutzer.getIdentifierForItem());
	    model.getVerfuegbareRollenIds().forEach(rId -> zuordnungBenutzer.put(rId, hasBenutzerRollenId(benutzer, rId)));
	}
	
	model.setRollenZuordnung(rollenZuordnung);
    }

    private boolean hasBenutzerRollenId(BenutzerModel benutzer, String rollenId) {
	return benutzer.getRollen().stream().anyMatch(rolleModel -> rolleModel.getRollenId().equals(rollenId));
    }

    /**
     * Weist den im Model gespeicherten Benutzern die im Model gespeicherte
     * Rolle zu.
     * 
     * @param model
     *            das Model.
     */
    public void rolleZuweisen(RolleZuweisenModel model) {
	speichereAusgewaehlteBenutzernamen(model);

	RolleModel rolle = getAusgewaehlteRolle(model);

	try {
	    getAwkWrapper().weiseRolleZu(rolle, new ArrayList<>(model.getAusgewaehlteBenutzernamen()));
	    
	    getMessageController().writeSuccessMessage(MessageSourceHolder
		    .getMessage(HinweisSchluessel.ROLLENZUWEISUNG_BENUTZER_AKTUALISIERT));
	} catch (BenutzerverwaltungBusinessException exception) {
	    zeigeNachricht(exception);
	}
    }

    /**
     * Entzieht den im Model gespeicherten Benutzern die im Model gespeicherte
     * Rolle.
     * 
     * @param model
     *            das Model.
     */
    public void rolleEntziehen(RolleZuweisenModel model) {
	speichereAusgewaehlteBenutzernamen(model);

	RolleModel rolle = getAusgewaehlteRolle(model);

	try {
	    getAwkWrapper().entzieheRolle(rolle, new ArrayList<>(model.getAusgewaehlteBenutzernamen()));
	    
	    getMessageController().writeSuccessMessage(MessageSourceHolder
		    .getMessage(HinweisSchluessel.ROLLENZUWEISUNG_BENUTZER_AKTUALISIERT));
	} catch (BenutzerverwaltungBusinessException exception) {
	    zeigeNachricht(exception);
	}
    }

    private RolleModel getAusgewaehlteRolle(RolleZuweisenModel model) {
	return model.getAlleRollen().stream()
		    		    .filter(rolle -> model.getRollenIdZurZuweisung().equals(rolle.getRollenId()))
		    		    .findFirst()
		    		    .orElse(null);
    }

    @Override
    protected SuchergebnisModel<BenutzerModel> sucheEntitaet(BenutzerSuchkriterienModel kriterien,
	    Sortierung sortierung, Paginierung paginierung) throws BenutzerverwaltungValidationException {
	return getAwkWrapper().sucheBenutzer(kriterien, sortierung, paginierung);
    }
}
