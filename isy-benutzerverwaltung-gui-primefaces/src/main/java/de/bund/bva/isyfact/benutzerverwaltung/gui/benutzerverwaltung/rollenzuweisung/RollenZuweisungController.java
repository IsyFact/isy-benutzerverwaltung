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


import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.FehlerSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.ValidierungSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.BenutzerverwaltungAwkWrapper;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzersuchen.BenutzerSuchkriterienModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.controller.AbstractBenutzerverwaltungController;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.HinweisSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.RolleModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.SuchergebnisModel;
import de.bund.bva.isyfact.common.web.global.MessageController;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Dieser Controller stellt die Funktionalitaet zur Rollenzuweisung-Maske
 * bereit.
 *
 * @author Capgemini, Jonas Zitz
 */
public class RollenZuweisungController extends AbstractBenutzerverwaltungController<RollenZuweisungModel> {

    public RollenZuweisungController(MessageController messageController, MessageSource messageSource,
        BenutzerverwaltungAwkWrapper awkWrapper) {
        super(messageController, messageSource, awkWrapper);
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
            SuchergebnisModel<BenutzerModel> alleBenutzer =
                getAwkWrapper().sucheBenutzer(new BenutzerSuchkriterienModel(), null, null);

            model.setAlleBenutzer(alleBenutzer.getTrefferliste());
            model.setAlleRollen(getAwkWrapper().leseAlleRollen());
        } catch (BenutzerverwaltungValidationException validatorException) {
            erzeugeNachrichten(validatorException);
        }
        model.setSelektierteRollenId(null);
        model.setBenutzerZuRolle(new ArrayList<String>());

        // Entferne Passwort. Dieses wird nicht benoetigt. Der Awf setzt das
        // alte Passwort bei einer
        // Benutzer-Aktualisierung.
        for (BenutzerModel b : model.getAlleBenutzer()) {
            b.setPasswort(null);
        }
    }

    /**
     * Ermittelt zu einer {@link RollenZuweisungModel#getSelektierteRollenId()
     * selektierten Rolle} alle {@link BenutzerModel}, die dieser Rolle
     * zugewiesen sind.
     *
     * @param model ist das {@link RollenZuweisungModel}
     */
    public void ermittleBenutzerZuRolle(RollenZuweisungModel model) {
        model.getBenutzerZuRolle().clear();
        for (BenutzerModel benutzer : model.getAlleBenutzer()) {
            for (RolleModel benutzerRolle : benutzer.getRollen()) {
                if (benutzerRolle.getRollenId().equals(model.getSelektierteRollenId())) {
                    model.getBenutzerZuRolle().add(benutzer.getBenutzername());
                    break;
                }
            }
        }
    }

    /**
     * Aktualisiert alle {@link BenutzerModel} durch Aktualisierung der
     * gewaehlten Rollen.
     *
     * @param model ist das Model.
     */
    public void aktualisiereRollenAllerBenutzer(RollenZuweisungModel model) {
        String selektierteRollenId = model.getSelektierteRollenId();
        if (selektierteRollenId == null) {
            getMessageController().writeInfoMessage(
                MessageSourceHolder.getMessage(ValidierungSchluessel.MSG_KEINE_ROLLE_AUSGEWAEHLT));
            return;
        }

        Optional<RolleModel> selektierteRolle =
            model.getAlleRollen().stream().filter(rolle -> rolle.getRollenId().equals(selektierteRollenId))
                .findFirst();
        if (selektierteRolle.isPresent()) {
            List<String> benutzerRolleZuweisen = new ArrayList<>();
            List<String> benutzerRolleEntziehen = new ArrayList<>();
            for (BenutzerModel benutzer : model.getAlleBenutzer()) {
                boolean benutzerZuRolleZuweisen =
                    model.getBenutzerZuRolle().contains(benutzer.getBenutzername());
                boolean benutzerHatRolle = pruefeBenutzerHatRolle(benutzer, selektierteRollenId);

                // Benutzer hat die Rolle nicht, ist aber selektiert => Rolle zuweisen
                if (!benutzerHatRolle && benutzerZuRolleZuweisen) {
                    benutzerRolleZuweisen.add(benutzer.getBenutzername());
                }
                // Benutzer hat die Rolle, ist aber nicht selektiert => Rolle entziehen
                if (benutzerHatRolle && !benutzerZuRolleZuweisen) {
                    benutzerRolleEntziehen.add(benutzer.getBenutzername());
                }

                try {
                    getAwkWrapper().weiseRolleZu(selektierteRolle.get(), benutzerRolleZuweisen);
                    getAwkWrapper().entzieheRolle(selektierteRolle.get(), benutzerRolleEntziehen);
                } catch (BenutzerverwaltungBusinessException validationException) {
                    erzeugeNachrichten(validationException);
                }
            }

            getMessageController().writeSuccessMessage(
                MessageSourceHolder.getMessage(HinweisSchluessel.ROLLENZUWEISUNG_BENUTZER_AKTUALISIERT));
        } else {
            getMessageController().writeWarnMessage(
                MessageSourceHolder.getMessage(ValidierungSchluessel.MSG_ROLLE_NICHT_VORHANDEN),
                MessageSourceHolder.getMessage(FehlerSchluessel.MSG_EINGABEDATEN_UNGUELTIG));
        }
    }

    /**
     * Prueft, ob ein {@link BenutzerModel Benutzer} eine definierte
     * {@link RolleModel Rolle} Besitzt.
     *
     * @param benutzer ist der {@link BenutzerModel Benutzer}
     * @param rollenId ist die {@link RolleModel Rolle}
     * @return {@link Boolean#TRUE}, wenn der {@link BenutzerModel Benutzer} ueber die {@link RolleModel
     * Rolle} verfuegt. {@link Boolean#FALSE} ansonsten.
     */
    private boolean pruefeBenutzerHatRolle(BenutzerModel benutzer, String rollenId) {
        if (benutzer.getRollen() == null || benutzer.getRollen().isEmpty()) {
            return false;
        }

        for (RolleModel benutzerRolle : benutzer.getRollen()) {
            if (rollenId.equals(benutzerRolle.getRollenId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected Class<RollenZuweisungModel> getMaskenModelKlasseZuController() {
        return RollenZuweisungModel.class;
    }

}
