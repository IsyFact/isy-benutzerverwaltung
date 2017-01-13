package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzersuchen;

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


import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.FehlerSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.controller.AbstractSuchDataTableController;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.SuchergebnisModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.util.AuswahlHelper;
import de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableModel.DatatableOperationMode;
import de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTablePaginationModel.PaginationType;
import de.bund.bva.pliscommon.sicherheit.Sicherheit;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author msg systems ag, Björn Saxe
 */
public class BenutzerSuchenController extends
	AbstractSuchDataTableController<BenutzerModel, BenutzerSuchkriterienModel, BenutzerSuchenModel, BenutzerSortierattribut> {

    private Sicherheit<?> sicherheit;

    public void filterZuruecksetzen(BenutzerSuchenModel model) {
	model.setSuchkriterien(new BenutzerSuchkriterienModel());
    }

    /**
     * Diese Methode initialisiert das Filterkriterien Model. Es werden die
     * Filter zurueckgesetzt und DropDown-Menues mit Werten belegt.
     *
     * @param model
     *            das Model.
     */
    public void initialisiereModel(BenutzerSuchenModel model) {
	if (model.getSuchkriterien() == null) {
	    model.setMode(DatatableOperationMode.SERVER);

	    model.setVerfuegbareRollen(AuswahlHelper.erstelleRollenDropDown(getAwkWrapper().getRollen()));
	    model.setVerfuegbareBenutzerStatus(AuswahlHelper.erstelleBenutzerStatusDropDown());

	    model.setSuchkriterien(new BenutzerSuchkriterienModel());
	    model.getSortModel().setProperty(BenutzerSortierattribut.NACHNAME.name());
	    model.getPaginationModel().setType(PaginationType.NORMAL);
	    model.getPaginationModel().setPageSize(10);
	}
    }

    /**
     * Löscht einen Benutzer.
     *
     * @param model Benutzer, der gelöscht werden soll
     */
    public boolean benutzerLoeschen(BenutzerSuchenModel model) {
	try {
	    getAwkWrapper().loescheBenutzer(model.getAusgewaehlterTreffer());
	    // Das Leeren des Caches zwingt alle Benutzer intern zur erneuten
	    // Anmeldung.
	    // Der gelöschte Benutzer stößt auf einen Fehler und ist so nicht
	    // mehr angemeldet.
	    sicherheit.leereCache();
	    return true;
	} catch (DataIntegrityViolationException e) {
	    zeigeNachricht(new BenutzerverwaltungBusinessException(FehlerSchluessel.BENUTZER_LOESCHEN_NICHT_MOEGLICH,
		    model.getAusgewaehlterTreffer().getBenutzername()));
	    return false;
	} catch (BenutzerverwaltungBusinessException exception) {
	    zeigeNachricht(exception);
	    return false;
	}
    }

    @Override
    protected SuchergebnisModel<BenutzerModel> sucheEntitaet(BenutzerSuchkriterienModel kriterien,
	    Sortierung sortierung, Paginierung paginierung) throws BenutzerverwaltungValidationException {
	return getAwkWrapper().sucheBenutzer(kriterien, sortierung, paginierung);
    }

    @Required
    public void setSicherheit(Sicherheit<?> sicherheit) {
	this.sicherheit = sicherheit;
    }
}
