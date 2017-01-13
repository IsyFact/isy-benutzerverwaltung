package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.passwortaendern;

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
import de.bund.bva.isyfact.common.web.global.MessageController;
import de.bund.bva.pliscommon.aufrufkontext.AufrufKontextVerwalter;
import de.bund.bva.pliscommon.sicherheit.Sicherheit;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.springframework.context.MessageSource;

/**
 * Controller zum Ändern des eigenen Passworts.
 *
 * @author msg systems ag, Stefan Dellmuth
 * @version $Id: RolleBearbeitenController.java 41870 2013-07-25 13:54:34Z jozitz $
 */
public class PasswortAendernController extends AbstractBenutzerverwaltungController<PasswortAendernModel> {

    private final AufrufKontextVerwalter aufrufKontextVerwalter;

    private final Sicherheit<?> sicherheit;

    public PasswortAendernController(MessageController messageController, MessageSource messageSource,
        BenutzerverwaltungAwkWrapper awkWrapper, AufrufKontextVerwalter aufrufKontextVerwalter,
        Sicherheit<?> sicherheit) {
        super(messageController, messageSource, awkWrapper);
        this.aufrufKontextVerwalter = aufrufKontextVerwalter;
        this.sicherheit = sicherheit;
    }

    @Override
    public void initialisiereModel(PasswortAendernModel model) {
        model.setBenutzername(aufrufKontextVerwalter.getAufrufKontext().getDurchfuehrenderBenutzerKennung());
    }

    /**
     * Ändert das Passwort des Benutzers.
     *
     * @param model das Model
     */
    public void passwortAendern(PasswortAendernModel model) {
        try {
            getAwkWrapper().setzePasswort(model);
            getMessageController().writeSuccessMessage(
                MessageSourceHolder.getMessage(HinweisSchluessel.BENUTZER_SELBST_PASSWORT_GEAENDERT));
            // Alle Nutzer müssen sich nach einer Passwortänderung neu authentifizieren.
            sicherheit.leereCache();
        } catch (BenutzerverwaltungBusinessException validationException) {
            erzeugeNachrichten(validationException);
        }

    }

    @Override
    protected Class<PasswortAendernModel> getMaskenModelKlasseZuController() {
        return PasswortAendernModel.class;
    }

}
