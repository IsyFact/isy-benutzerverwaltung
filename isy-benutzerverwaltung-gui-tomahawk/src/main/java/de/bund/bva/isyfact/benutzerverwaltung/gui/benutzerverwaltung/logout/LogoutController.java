package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.logout;

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
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.controller.AbstractBenutzerverwaltungController;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.EreignissSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.sicherheit.BenutzerverwaltungAufrufKontextImpl;
import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.bund.bva.isyfact.logging.LogKategorie;
import de.bund.bva.pliscommon.aufrufkontext.AufrufKontextVerwalter;
import org.springframework.stereotype.Controller;

/**
 * Controller des Login Flows
 *
 * @author msg systems ag, Andreas Schubert
 */
@Controller
public class LogoutController extends AbstractBenutzerverwaltungController<LogoutModel> {

    private static final IsyLogger LOG = IsyLoggerFactory.getLogger(LogoutController.class);

    private AufrufKontextVerwalter<BenutzerverwaltungAufrufKontextImpl> aufrufKontextVerwalter;

    @Override
    public void initialisiereModel(LogoutModel model) {
    }

    /**
     * Führt den Logout-Vorgang aus.
     */
    public boolean performLogout() {

        LOG.infoFachdaten(LogKategorie.JOURNAL, EreignissSchluessel.MSG_LOGIN_STARTED,
            "Führe Logout aus für Benutzer {}",
            aufrufKontextVerwalter.getAufrufKontext().getDurchfuehrenderSachbearbeiterName());

        try {
            getBenutzerverwaltungAwkWrapper().speichereAbmeldung(
                aufrufKontextVerwalter.getAufrufKontext().getDurchfuehrenderBenutzerKennung());
        } catch (BenutzerverwaltungBusinessException validationException) {
            zeigeNachricht(validationException);
        }
        aufrufKontextVerwalter.setAufrufKontext(new BenutzerverwaltungAufrufKontextImpl());

        return true;
    }

    public AufrufKontextVerwalter<BenutzerverwaltungAufrufKontextImpl> getAufrufKontextVerwalter() {
        return aufrufKontextVerwalter;
    }

    public void setAufrufKontextVerwalter(
        AufrufKontextVerwalter<BenutzerverwaltungAufrufKontextImpl> aufrufKontextVerwalter) {
        this.aufrufKontextVerwalter = aufrufKontextVerwalter;
    }

    @Override
    protected Class<LogoutModel> getMaskenModelKlasseZuController() {
        return LogoutModel.class;
    }

}
