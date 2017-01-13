package de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.rollebearbeiten;

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


import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.HinweisSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.RolleModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.awkwrapper.RollenverwaltungAwkWrapper;
import de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.common.controller.AbstractRollenverwaltungController;
import de.bund.bva.isyfact.common.web.global.MessageController;
import de.bund.bva.pliscommon.sicherheit.Sicherheit;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.springframework.context.MessageSource;

import java.util.Objects;

/**
 * Controller zum Bearbeiten von Rollen.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class RolleBearbeitenController extends AbstractRollenverwaltungController<RolleBearbeitenModel> {

    private final Sicherheit<?> sicherheit;

    public RolleBearbeitenController(MessageController messageController, MessageSource messageSource,
        RollenverwaltungAwkWrapper awkWrapper, Sicherheit<?> sicherheit) {
        super(messageController, messageSource, awkWrapper);
        this.sicherheit = sicherheit;
    }

    @Override
    public void initialisiereModel(RolleBearbeitenModel model) {
    }

    /**
     * Diese Methode setzt die übergebene Rolle in das Model und initialisiert es.
     *
     * @param model das Model
     * @param rolle die übergebene Rolle
     */
    public void setzeRolle(RolleBearbeitenModel model, RolleModel rolle) {
        model.setRolle(rolle);
        model.setRollenIdVorVerarbeitung(rolle.getRollenId());
    }

    /**
     * Ändert die Rolle.
     *
     * @param model das Model
     */
    public void aendereRolle(RolleBearbeitenModel model) {
        try {
            RolleModel ergebnis =
                getAwkWrapper().aendereRolle(model.getRollenIdVorVerarbeitung(), model.getRolle());
            getMessageController().writeSuccessMessage(MessageSourceHolder
                .getMessage(HinweisSchluessel.ROLLE_GEAENDERT, ergebnis.getRollenId()));
            if (!Objects.equals(ergebnis.getRollenId(), model.getRollenIdVorVerarbeitung())) {
                // Bei Änderung der Rollen-ID müssen alle Benutzer neu geladen werden.
                sicherheit.leereCache();
            }
        } catch (BenutzerverwaltungValidationException validationException) {
            erzeugeNachrichten(validationException);
        }
    }

    @Override
    protected Class<RolleBearbeitenModel> getMaskenModelKlasseZuController() {
        return RolleBearbeitenModel.class;
    }

}
