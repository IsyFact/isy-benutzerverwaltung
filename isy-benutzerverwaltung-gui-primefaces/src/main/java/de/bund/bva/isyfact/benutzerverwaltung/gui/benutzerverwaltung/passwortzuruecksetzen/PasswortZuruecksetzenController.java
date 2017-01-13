package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.passwortzuruecksetzen;

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
import de.bund.bva.pliscommon.sicherheit.Sicherheit;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.springframework.context.MessageSource;

/**
 * Controller zum Bearbeiten von Benutzern.
 *
 * @author Capgemini, Jonas Zitz
 * @version $Id: RolleBearbeitenController.java 41870 2013-07-25 13:54:34Z jozitz $
 */
public class PasswortZuruecksetzenController
    extends AbstractBenutzerverwaltungController<PasswortZuruecksetzenModel> {

    private final Sicherheit<?> sicherheit;

    public PasswortZuruecksetzenController(MessageController messageController, MessageSource messageSource,
        BenutzerverwaltungAwkWrapper awkWrapper, Sicherheit<?> sicherheit) {
        super(messageController, messageSource, awkWrapper);
        this.sicherheit = sicherheit;
    }

    @Override
    public void initialisiereModel(PasswortZuruecksetzenModel model) {
    }

    /**
     * Diese Methode setzt den uebergebenen {@link BenutzerModel} in das Model und initialisiert es.
     *
     * @param model    das Model
     * @param benutzer der uebergebene {@link BenutzerModel}
     */
    public void setzeBenutzer(PasswortZuruecksetzenModel model, BenutzerModel benutzer) {
        model.setBenutzer(benutzer);
    }

    /**
     * Funktion zum Aktualisieren / Bearbeiten eines Benutzers
     *
     * @param model das {@link PasswortZuruecksetzenModel}
     */
    public void passwortZuruecksetzen(PasswortZuruecksetzenModel model) {
        try {
            getAwkWrapper().setzePasswortZurueck(model.getPasswortZuruecksetzen());
            getMessageController().writeSuccessMessage(
                MessageSourceHolder.getMessage(HinweisSchluessel.BENUTZER_PASSWORT_ZURUECKGESETZT));
            // Alle Nutzer müssen sich nach einer Passwortänderung neu authentifizieren.
            sicherheit.leereCache();
        } catch (BenutzerverwaltungBusinessException validationException) {
            erzeugeNachrichten(validationException);
        }

    }

    @Override
    protected Class<PasswortZuruecksetzenModel> getMaskenModelKlasseZuController() {
        return PasswortZuruecksetzenModel.class;
    }

}
