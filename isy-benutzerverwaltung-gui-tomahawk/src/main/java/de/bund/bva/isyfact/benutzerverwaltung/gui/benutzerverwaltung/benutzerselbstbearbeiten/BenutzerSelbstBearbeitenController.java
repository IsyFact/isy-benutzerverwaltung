package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzerselbstbearbeiten;

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

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.daten.BenutzerBearbeitenSelbst;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzerbearbeiten.BenutzerBearbeitenModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.controller.AbstractBenutzerverwaltungController;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.HinweisSchluessel;
import de.bund.bva.pliscommon.aufrufkontext.AufrufKontextVerwalter;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.springframework.beans.factory.annotation.Required;

public class BenutzerSelbstBearbeitenController extends
    AbstractBenutzerverwaltungController<BenutzerSelbstBearbeitenModel> {

    private AufrufKontextVerwalter<?> aufrufKontextVerwalter;

    /**
     * Funktion zur Selbstbearbeitung durch den Benutzer.
     *
     * @param model
     *     das {@link BenutzerBearbeitenModel}
     */
    public void benutzerSelbstBearbeiten(BenutzerSelbstBearbeitenModel model) {
        try {
            BenutzerBearbeitenSelbst bearbeitenSelbst = new BenutzerBearbeitenSelbst();
            bearbeitenSelbst.setBehoerde(model.getBenutzer().getBehoerde());
            bearbeitenSelbst.setBenutzername(model.getBenutzer().getBenutzername());
            bearbeitenSelbst.setEmailAdresse(model.getBenutzer().getEmailAdresse());
            bearbeitenSelbst.setNachname(model.getBenutzer().getNachname());
            bearbeitenSelbst.setTelefonnummer(model.getBenutzer().getTelefonnummer());
            bearbeitenSelbst.setVorname(model.getBenutzer().getVorname());

            getBenutzerverwaltungAwkWrapper().aendereBenutzerSelbst(bearbeitenSelbst);

            getMessageController().writeSuccessMessage(MessageSourceHolder
                .getMessage(HinweisSchluessel.BENUTZER_AKTUALISIERT, model.getBenutzer().getBenutzername()));
        } catch (BenutzerverwaltungBusinessException validationException) {
            zeigeNachricht(validationException);
        }
    }

    @Override
    public void initialisiereModel(BenutzerSelbstBearbeitenModel model) {
        String benutzername = aufrufKontextVerwalter.getAufrufKontext().getDurchfuehrenderBenutzerKennung();

        try {
            model.setBenutzer(getBenutzerverwaltungAwkWrapper().leseBenutzer(benutzername));
        } catch (BenutzerverwaltungBusinessException validationException) {
            getMessageController().writeException(validationException);
        }
    }

    @Required
    public void setAufrufKontextVerwalter(AufrufKontextVerwalter<?> aufrufKontextVerwalter) {
        this.aufrufKontextVerwalter = aufrufKontextVerwalter;
    }

    @Override
    protected Class<BenutzerSelbstBearbeitenModel> getMaskenModelKlasseZuController() {
        return BenutzerSelbstBearbeitenModel.class;
    }
}