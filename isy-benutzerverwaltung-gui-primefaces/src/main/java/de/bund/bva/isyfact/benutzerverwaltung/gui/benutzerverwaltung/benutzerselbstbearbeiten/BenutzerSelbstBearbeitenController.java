package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzerselbstbearbeiten;

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
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.BenutzerverwaltungAwkWrapper;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.controller.AbstractBenutzerverwaltungController;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.HinweisSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;
import de.bund.bva.isyfact.common.web.global.MessageController;
import de.bund.bva.pliscommon.aufrufkontext.AufrufKontextVerwalter;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.springframework.context.MessageSource;

/**
 * Controller zum Bearbeiten von Benutzern.
 *
 * @author msg systems ag, Stefan Dellmuth
 * @version $Id: RolleBearbeitenController.java 41870 2013-07-25 13:54:34Z jozitz $
 */
public class BenutzerSelbstBearbeitenController
    extends AbstractBenutzerverwaltungController<BenutzerSelbstBearbeitenModel> {

    private final AufrufKontextVerwalter<?> aufrufKontextVerwalter;

    public BenutzerSelbstBearbeitenController(MessageController messageController,
        MessageSource messageSource, BenutzerverwaltungAwkWrapper awkWrapper,
        AufrufKontextVerwalter<?> aufrufKontextVerwalter) {
        super(messageController, messageSource, awkWrapper);
        this.aufrufKontextVerwalter = aufrufKontextVerwalter;
    }

    @Override
    public void initialisiereModel(BenutzerSelbstBearbeitenModel model) {
        String benutzername = aufrufKontextVerwalter.getAufrufKontext().getDurchfuehrenderBenutzerKennung();
        try {
            model.setBenutzer(getAwkWrapper().leseBenutzer(benutzername));
        } catch (BenutzerverwaltungBusinessException validationException) {
            erzeugeNachrichten(validationException);
        }
    }

    /**
     * Funktion zum Aktualisieren / Bearbeiten eines Benutzers
     *
     * @param model das {@link BenutzerSelbstBearbeitenModel}
     */
    public void benutzerSpeichern(BenutzerSelbstBearbeitenModel model) {
        try {
            BenutzerModel ergebnis = getAwkWrapper().aendereBenutzerSelbst(model);
            getMessageController().writeSuccessMessage(MessageSourceHolder
                .getMessage(HinweisSchluessel.BENUTZER_AKTUALISIERT, ergebnis.getBenutzername()));
        } catch (BenutzerverwaltungBusinessException validationException) {
            erzeugeNachrichten(validationException);
        }
    }

    @Override
    protected Class<BenutzerSelbstBearbeitenModel> getMaskenModelKlasseZuController() {
        return BenutzerSelbstBearbeitenModel.class;
    }

}
