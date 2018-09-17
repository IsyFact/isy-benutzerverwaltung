package de.bund.bva.isyfact.benutzerverwaltung.gui.selfservice.passwortzuruecksetzen;

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


import java.util.Collections;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.ValidierungSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.controller.AbstractBenutzerverwaltungController;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.HinweisSchluessel;
import de.bund.bva.isyfact.common.web.validation.ValidationMessage;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;

/**
 * Controller zum Bearbeiten von Benutzern.
 *
 * @author msg systems ag, Bjoern Saxe
 * @author msg systems ag, Alexander Salvanos
 */
public class SelfServicePasswortZuruecksetzenController
    extends AbstractBenutzerverwaltungController<SelfServicePasswortZuruecksetzenModel> {

    @Override
    public void initialisiereModel(SelfServicePasswortZuruecksetzenModel model) {
        model.setPasswortZurueckgesetzt(false);
        model.setTokenGueltig(false);
    }

    @Override
    protected Class<SelfServicePasswortZuruecksetzenModel> getMaskenModelKlasseZuController() {
        return SelfServicePasswortZuruecksetzenModel.class;
    }

    public void ueberpruefeToken(SelfServicePasswortZuruecksetzenModel model, String token) {
        try {
            String benutzername = getSelfServiceAwkWrapper().holeBenutzernameZuToken(token);
            model.setTokenGueltig(true);
            model.setBenutzername(benutzername);
        } catch (BenutzerverwaltungBusinessException e) {
            getMessageController().writeErrorMessage(
                MessageSourceHolder.getMessage(HinweisSchluessel.SELFSERVICE_TOKEN_UNGUELTIG),
                MessageSourceHolder.getMessage(HinweisSchluessel.SELFSERVICE_TOKEN_UNGUELTIG));
        }
    }

    public void passwortZuruecksetzen(SelfServicePasswortZuruecksetzenModel model) {
        if (model.getPasswort().equals(model.getPasswortWiederholung())) {
            try {
                getSelfServiceAwkWrapper().passwortZuruecksetzen(model.getBenutzername(), model.getPasswort(),
                    model.getPasswortWiederholung());
                getMessageController().writeSuccessMessage(
                    MessageSourceHolder.getMessage(HinweisSchluessel.BENUTZER_PASSWORT_ZURUECKGESETZT));
                model.setPasswortZurueckgesetzt(true);
            } catch (BenutzerverwaltungBusinessException e) {
                zeigeNachricht(e);
            }
        } else {
            getValidationController().processValidationMessages(Collections.singletonList(
                new ValidationMessage("VA", "passwortWiederholung", "passwortWiederholung",
                    MessageSourceHolder
                        .getMessage(ValidierungSchluessel.MSG_PASSWORT_AENDERN_UNTERSCHIEDLICH))));
        }
    }

}
