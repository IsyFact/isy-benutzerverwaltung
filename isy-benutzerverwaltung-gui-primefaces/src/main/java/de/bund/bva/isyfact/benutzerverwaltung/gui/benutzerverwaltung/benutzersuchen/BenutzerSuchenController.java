package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzersuchen;

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


import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.FehlerSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.BenutzerverwaltungAwkWrapper;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.controller.SucheController;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.HinweisSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.SuchergebnisModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.TrefferlisteModel;
import de.bund.bva.isyfact.common.web.global.MessageController;
import de.bund.bva.pliscommon.sicherheit.Sicherheit;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.webflow.execution.RequestContextHolder;

import java.util.List;
import java.util.Objects;

/**
 * Controller für die Suche nach Benutzern.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerSuchenController extends SucheController<BenutzerSuchenModel> {

    private final BenutzerverwaltungAwkWrapper awkWrapper;

    private final Sicherheit<?> sicherheit;

    public BenutzerSuchenController(MessageController messageController, MessageSource messageSource,
        BenutzerverwaltungAwkWrapper awkWrapper, Sicherheit<?> sicherheit) {
        super(messageController, messageSource);
        this.awkWrapper = awkWrapper;
        this.sicherheit = sicherheit;
    }

    /**
     * Diese Methode initialisiert das Filterkriterien Model. Es werden die Filter zurueckgesetzt und
     * DropDown-Menues mit Werten belegt.
     *
     * @param benutzerSuchenModel das Model.
     */
    public void initialisiereModel(BenutzerSuchenModel benutzerSuchenModel) {
        try {
            benutzerSuchenModel.setAlleRollen(awkWrapper.leseAlleRollen());
        } catch (BenutzerverwaltungValidationException e) {
            erzeugeNachrichten(e);
        }
        benutzerSuchenModel.setTrefferliste(new BenutzerTrefferlisteModel());
        filterZuruecksetzen(benutzerSuchenModel);
    }

    public void filterZuruecksetzen(BenutzerSuchenModel benutzerSuchenModel) {
        benutzerSuchenModel.getTrefferliste().setSuchkriterien(new BenutzerSuchkriterienModel());
    }

    /**
     * Löscht einen Benutzer.
     *
     * @param model Benutzer, der gelöscht werden soll
     * @return {@code true}, wenn das Löschen erfolgreich war, ansonsten {@code false}.
     */
    public boolean benutzerLoeschen(BenutzerModel model) {
        try {
            awkWrapper.loescheBenutzer(model);
            // Das Leeren des Caches zwingt alle Benutzer intern zur erneuten Anmeldung.
            // Der gelöschte Benutzer stößt auf einen Fehler und ist so nicht mehr angemeldet.
            sicherheit.leereCache();
            getMessageController().writeSuccessMessage(MessageSourceHolder
                .getMessage(HinweisSchluessel.BENUTZER_GELOESCHT, model.getBenutzername()));
            return true;
        } catch (DataIntegrityViolationException e) {
            // Dieser Fehler passiert während des Abschlusses der Transaktion.
            // Der Benutzer kann nicht gelöscht werden, weil von irgendwoher auf sie verwiesen wird.
            erzeugeNachrichten(
                new BenutzerverwaltungBusinessException(FehlerSchluessel.BENUTZER_LOESCHEN_NICHT_MOEGLICH,
                    model.getBenutzername()));
            return false;
        } catch (BenutzerverwaltungBusinessException validationException) {
            erzeugeNachrichten(validationException);
            return false;
        }
    }

    @Override
    protected Class<BenutzerSuchenModel> getMaskenModelKlasseZuController() {
        return BenutzerSuchenModel.class;
    }

    /**
     * Model für die Trefferliste. Ist als innere Klasse des Controllers implementiert, da
     * Models eigentlich keinen direkten Zugang zum AWK-Wrapper haben dürfen.
     *
     * @author msg systems ag, Stefan Dellmuth
     */
    private static class BenutzerTrefferlisteModel
        extends TrefferlisteModel<BenutzerModel, BenutzerSuchkriterienModel> {

        @Override
        protected SuchergebnisModel<BenutzerModel> suche(BenutzerSuchkriterienModel suchkriterien,
            Sortierung sortierung, Paginierung paginierung) throws BenutzerverwaltungValidationException {
            BenutzerverwaltungAwkWrapper awkWrapper =
                RequestContextHolder.getRequestContext().getActiveFlow().getApplicationContext()
                    .getBean(BenutzerverwaltungAwkWrapper.class);
            return awkWrapper.sucheBenutzer(suchkriterien, sortierung, paginierung);
        }

        @Override
        protected Sortierattribut ermittleSortierattribut(String sortierAttribut) {
            try {
                return BenutzerSortierattribut.valueOf(sortierAttribut);
            } catch (IllegalArgumentException | NullPointerException e) {
                return BenutzerSortierattribut.getStandard();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public BenutzerModel getRowData(String rowKey) {
            for (BenutzerModel treffer : (List<BenutzerModel>) getWrappedData()) {
                if (Objects.equals(rowKey, getRowKey(treffer))) {
                    return treffer;
                }
            }
            return null;
        }

        @Override
        public Object getRowKey(BenutzerModel object) {
            return object.getBenutzername();
        }
    }

}
