package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.passwortzuruecksetzen;

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

import java.util.Arrays;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.ValidierungSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.controller.AbstractBenutzerverwaltungController;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.HinweisSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;
import de.bund.bva.isyfact.common.web.validation.ValidationMessage;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;

/**
 * Controller zum Zurücksetzen des Passworts.
 *
 * @author msg systems ag, Björn Saxe
 */
public class BenutzerPasswortZuruecksetzenController
    extends AbstractBenutzerverwaltungController<BenutzerPasswortZuruecksetzenModel> {

    public void setzeBenutzer(BenutzerPasswortZuruecksetzenModel model, BenutzerModel benutzer) {
        model.setBenutzername(benutzer.getBenutzername());
    }

    public void setzePasswortZurueck(BenutzerPasswortZuruecksetzenModel model) {

        if (model.getPasswort().equals(model.getPasswortWiederholung())) {
            try {
                getBenutzerverwaltungAwkWrapper()
                    .setzePasswortZurueck(model.getBenutzername(), model.getPasswort(),
                        model.getPasswortWiederholung());

                getMessageController().writeSuccessMessage(
                    MessageSourceHolder.getMessage(HinweisSchluessel.BENUTZER_PASSWORT_ZURUECKGESETZT));
            } catch (BenutzerverwaltungBusinessException exception) {
                zeigeNachricht(exception);
            }
        } else {
            getValidationController().processValidationMessages(Arrays.asList(
                new ValidationMessage("VA", "neuesPasswortWiederholung", "neuesPasswortWiederholung",
                    MessageSourceHolder
                        .getMessage(ValidierungSchluessel.MSG_PASSWORT_AENDERN_UNTERSCHIEDLICH))));
        }

    }

    @Override
    protected Class<BenutzerPasswortZuruecksetzenModel> getMaskenModelKlasseZuController() {
        return BenutzerPasswortZuruecksetzenModel.class;
    }

    /**
     * @see de.bund.bva.isyfact.common.web.global.RfGuiController#initialisiereModel(de.bund.bva.isyfact.common.web.global.AbstractMaskenModel)
     */
    @Override
    public void initialisiereModel(BenutzerPasswortZuruecksetzenModel model) {
    }
}
