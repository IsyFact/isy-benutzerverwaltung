package de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.rollesuchen;

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
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.RolleSortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.RolleModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.controller.AbstractSuchDataTableController;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.HinweisSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.SuchergebnisModel;
import de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTablePaginationModel.PaginationType;
import de.bund.bva.pliscommon.sicherheit.Sicherheit;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author msg systems ag, Björn Saxe
 *
 */
public class RolleSuchenController extends AbstractSuchDataTableController<RolleModel,
									   RolleSuchkriterienModel,
									   RolleSuchenModel,
									   RolleSortierattribut>
{
    private Sicherheit<?> sicherheit;

    /**
     * Diese Methode initialisiert das Suchmodel. Es werden die Filter zurueckgesetzt,
     * das Standard-Sortierattribut und Seitengroesse fuer den Paginator gesetzt.
     *
     * @param model das Model.
     */
    public void initialisiereModel(RolleSuchenModel model) {
	if (model.getSuchkriterien() == null)
	{
	    filterZuruecksetzen(model);
	    model.getSortModel().setProperty(RolleSortierattribut.getStandard().name());
	    model.getPaginationModel().setType(PaginationType.NORMAL);
	    model.getPaginationModel().setPageSize(10);
	}
    }
    
    public void filterZuruecksetzen(RolleSuchenModel rolleSuchenModel) {
        rolleSuchenModel.setSuchkriterien(new RolleSuchkriterienModel());
    }

    @Override
    protected SuchergebnisModel<RolleModel> sucheEntitaet(RolleSuchkriterienModel kriterien, Sortierung sortierung,
	    Paginierung paginierung) throws BenutzerverwaltungValidationException {
	return getAwkWrapper().sucheRollen(kriterien, sortierung, paginierung);
    }
    
    /**
     * Löscht eine Rolle.
     *
     * @param model Rolle, die gelöscht werden soll
     * @return {@code true}, wenn das Löschen erfolgreich war, ansonsten {@code false}.
     */
    public boolean rolleLoeschen(RolleSuchenModel model) {
        try {
            getAwkWrapper().loescheRolle(model.getAusgewaehlterTreffer());
            // Das Leeren des Caches zwingt alle Benutzer intern zur erneuten Anmeldung.
            // Der gelöschte Benutzer stößt auf einen Fehler und ist so nicht mehr angemeldet.
            //sicherheit.leereCache();
            getMessageController().writeSuccessMessage(MessageSourceHolder
                .getMessage(HinweisSchluessel.BENUTZER_GELOESCHT, model.getAusgewaehlterTreffer().getRollenId()));
            return true;
	} catch (DataIntegrityViolationException e) {
	    zeigeNachricht(new BenutzerverwaltungBusinessException(FehlerSchluessel.ROLLE_LOESCHEN_NICHT_MOEGLICH,
		    model.getAusgewaehlterTreffer().getRollenId()));
	    return false;
	} catch (BenutzerverwaltungBusinessException exception) {
            zeigeNachricht(exception);
            return false;
        }
    }

    /**
     * @param sicherheit the sicherheit to set
     */
    @Required
    public void setSicherheit(Sicherheit<?> sicherheit) {
        this.sicherheit = sicherheit;
    }
}
