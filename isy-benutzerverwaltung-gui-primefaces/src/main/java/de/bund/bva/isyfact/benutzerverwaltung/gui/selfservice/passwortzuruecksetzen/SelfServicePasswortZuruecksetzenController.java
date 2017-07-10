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


import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.daten.PasswortZuruecksetzenDaten;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.HinweisSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.selfservice.awkwrapper.SelfServiceAwkWrapper;
import de.bund.bva.isyfact.benutzerverwaltung.gui.selfservice.common.controller.AbstractSelfServiceController;
import de.bund.bva.isyfact.common.web.global.MessageController;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.springframework.context.MessageSource;

/**
 * Controller zum Bearbeiten von Benutzern.
 *
 * @author msg systems ag, Bjoern Saxe
 * @author msg systems ag, Alexander Salvanos
 */
public class SelfServicePasswortZuruecksetzenController
    extends AbstractSelfServiceController<SelfServicePasswortZuruecksetzenModel> {

    public SelfServicePasswortZuruecksetzenController(MessageController messageController, MessageSource messageSource,
                                                      SelfServiceAwkWrapper awkWrapper) {
        super(messageController, messageSource, awkWrapper);
    }

    @Override
    public void initialisiereModel(SelfServicePasswortZuruecksetzenModel model) {
        model.setTokenGueltig(false);
    }

    @Override
    protected Class<SelfServicePasswortZuruecksetzenModel> getMaskenModelKlasseZuController() {
        return SelfServicePasswortZuruecksetzenModel.class;
    }

    public void ueberpruefeToken(SelfServicePasswortZuruecksetzenModel model, String token) {
        try {
            String benutzername = getAwkWrapper().holeBenutzernameZuToken(token);
            model.setTokenGueltig(true);
            model.setBenutzername(benutzername);
        } catch (BenutzerverwaltungBusinessException e) {
            getMessageController().writeErrorMessage(
                    MessageSourceHolder.getMessage(HinweisSchluessel.BENUTZER_PASSWORT_ZURUECKGESETZT),
                    MessageSourceHolder.getMessage(HinweisSchluessel.BENUTZER_PASSWORT_ZURUECKGESETZT));
        }
    }

    public void passwortZuruecksetzen(SelfServicePasswortZuruecksetzenModel model) {
        try {
            PasswortZuruecksetzenDaten passwortZuruecksetzenDaten = new PasswortZuruecksetzenDaten();
            passwortZuruecksetzenDaten.setBenutzername(model.getBenutzername());
            passwortZuruecksetzenDaten.setNeuesPasswort(model.getPasswort());
            passwortZuruecksetzenDaten.setNeuesPasswortBestaetigung(model.getPasswortWiederholung());
            getAwkWrapper().passwortZuruecksetzen(passwortZuruecksetzenDaten);
            getMessageController().writeSuccessMessage(MessageSourceHolder.getMessage(HinweisSchluessel.BENUTZER_PASSWORT_ZURUECKGESETZT));
        } catch (BenutzerverwaltungBusinessException e) {
            getMessageController().writeAndLogException(e);
        }
    }

}
