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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.FehlerSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.ValidierungSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.BenutzerverwaltungAwkWrapper;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzersuchen.BenutzerSuchkriterienModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.controller.SucheController;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.HinweisSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.RolleModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.SuchergebnisModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.TrefferlisteModel;
import de.bund.bva.isyfact.common.web.global.MessageController;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.springframework.context.MessageSource;
import org.springframework.webflow.execution.RequestContextHolder;

/**
 * Dieser Controller stellt die Funktionalitaet zur Rollenzuweisung-Maske
 * bereit.
 *
 * @author Capgemini, Jonas Zitz
 */
public class RollenZuweisungController extends SucheController<RollenZuweisungModel> {

    private final BenutzerverwaltungAwkWrapper awkWrapper;

    public RollenZuweisungController(MessageController messageController, MessageSource messageSource,
        BenutzerverwaltungAwkWrapper awkWrapper) {
        super(messageController, messageSource);
        this.awkWrapper = awkWrapper;
    }

    /**
     * Diese Methode initialisiert das Model, sofern das Flag
     * <code>initialisierungErforderlich</code> auf {@link Boolean#TRUE} gesetzt
     * ist.
     *
     * @param model                       das Model
     * @param initialisierungErforderlich ist das Flag, das bei {@link Boolean#TRUE} eine Initialisierung
     *                                    erforderlich macht
     */
    public void initialisiereModel(RollenZuweisungModel model, boolean initialisierungErforderlich) {
        if (initialisierungErforderlich) {
            initialisiereModel(model);
        }
    }

    /**
     * Diese Methode initialisiert das Model.
     *
     * @param model das Model
     */
    public void initialisiereModel(RollenZuweisungModel model) {
        try {
            model.setAlleRollen(awkWrapper.leseAlleRollen());
        } catch (BenutzerverwaltungValidationException validatorException) {
            erzeugeNachrichten(validatorException);
        }
        model.setAusgewaehlteBenutzer(new ArrayList<>());
        model.setTrefferliste(new BenutzerTrefferlisteModel());
        filterZuruecksetzen(model);
    }

    public void filterZuruecksetzen(RollenZuweisungModel model) {
        model.getTrefferliste().setSuchkriterien(new BenutzerSuchkriterienModel());
    }

    public void rolleZuweisen(RollenZuweisungModel model, boolean rolleEntziehen) {
        Optional<RolleModel> ausgewaehlteRolle = model.getAlleRollen()
                                                      .stream()
                                                      .filter(r -> r.getRollenId().equals(model.getAusgewaehlteRollenId()))
                                                      .findFirst();

        if (ausgewaehlteRolle.isPresent()) {
            if (model.getAusgewaehlteBenutzer().isEmpty()) {
                getMessageController().writeWarnMessage(MessageSourceHolder.getMessage(HinweisSchluessel.KEIN_BENUTZER_AUSGEWAEHLT),
                    MessageSourceHolder.getMessage(HinweisSchluessel.KEIN_BENUTZER_AUSGEWAEHLT));
                return;
            }

            List<String> benutzernamen =
                model.getAusgewaehlteBenutzer().stream().map(BenutzerModel::getBenutzername).collect(Collectors.toList());

            try {
                if (rolleEntziehen) {
                    awkWrapper.entzieheRolle(ausgewaehlteRolle.get(), benutzernamen);
                } else {
                    awkWrapper.weiseRolleZu(ausgewaehlteRolle.get(), benutzernamen);
                }

                getMessageController().writeSuccessMessage(
                    MessageSourceHolder.getMessage(HinweisSchluessel.ROLLENZUWEISUNG_BENUTZER_AKTUALISIERT));
            } catch (BenutzerverwaltungBusinessException e) {
                erzeugeNachrichten(e);
            }
        } else {
            getMessageController().writeWarnMessage(
                MessageSourceHolder.getMessage(ValidierungSchluessel.MSG_ROLLE_NICHT_VORHANDEN),
                MessageSourceHolder.getMessage(FehlerSchluessel.MSG_EINGABEDATEN_UNGUELTIG));
        }
    }

    @Override
    protected Class<RollenZuweisungModel> getMaskenModelKlasseZuController() {
        return RollenZuweisungModel.class;
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
