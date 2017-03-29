package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.login;

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

import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.EreignissSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.sicherheit.BenutzerverwaltungAufrufKontextImpl;
import de.bund.bva.isyfact.common.web.global.AbstractGuiController;
import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.bund.bva.isyfact.logging.LogKategorie;
import de.bund.bva.pliscommon.aufrufkontext.AufrufKontextVerwalter;
import de.bund.bva.pliscommon.aufrufkontext.impl.AufrufKontextImpl;
import de.bund.bva.pliscommon.sicherheit.Sicherheit;
import de.bund.bva.pliscommon.sicherheit.common.exception.AuthentifizierungFehlgeschlagenException;
import de.bund.bva.pliscommon.sicherheit.common.exception.AuthentifizierungTechnicalException;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Controller;

/**
 * Controller des Login Flows
 *
 * @author msg systems ag, Maximilian Falter, Dirk Jäger
 */
@Controller
public class LoginController extends AbstractGuiController<LoginModel> {

    private static final IsyLogger LOG = IsyLoggerFactory.getLogger(LoginController.class);

    Sicherheit<AufrufKontextImpl> sicherheit;

    AufrufKontextVerwalter<BenutzerverwaltungAufrufKontextImpl> aufrufKontextVerwalter;

    /**
     * @param model Das Model
     */
    public void initialisiereModel(LoginModel model) {
        // wird für LoginController nicht benötigt
    }

    /**
     * Führt den Login-Vorgang aus.
     *
     * @param model   Das Modell
     * @param context Der Login-Kontext
     */
    public boolean performLogin(LoginModel model, MessageContext context) {

        LOG.infoFachdaten(LogKategorie.JOURNAL, EreignissSchluessel.MSG_LOGIN_STARTED,
            "Führe Login aus für Benutzer {}", model.getUsername());

        BenutzerverwaltungAufrufKontextImpl akontext = new BenutzerverwaltungAufrufKontextImpl();

        akontext.setDurchfuehrenderBenutzerKennung(model.getUsername());
        akontext.setDurchfuehrenderBenutzerPasswort(model.getPassword());

        try {
            sicherheit.getBerechtigungsManagerUndAuthentifiziere(akontext);

            LOG.info(LogKategorie.JOURNAL, EreignissSchluessel.MSG_LOGIN_SUCCESS,
                    "Authentifizierung erfolgreich");

            aufrufKontextVerwalter.setAufrufKontext(akontext);

        } catch (AuthentifizierungTechnicalException e) {
            LOG.info(LogKategorie.JOURNAL, EreignissSchluessel.MSG_LOGIN_FAILED,
                    "Authentifizierung fehlgeschlagen (Technischer Fehler)", e);

            context.addMessage(
                    new MessageBuilder().error().defaultText("Bei der Authentifizierung ist ein technischer Fehler aufgetreten.").build());
            return false;
        } catch (AuthentifizierungFehlgeschlagenException e) {
            LOG.info(LogKategorie.JOURNAL, EreignissSchluessel.MSG_LOGIN_FAILED,
                    "Authentifizierung fehlgeschlagen (Benutzername / Passwort falsch oder Benutzer nicht aktiviert)", e);

            context.addMessage(
                    new MessageBuilder().error().defaultText("Benutzername / Passwort falsch oder der Benutzer ist nicht aktiviert.").build());
            return false;
        }
        return true;
    }

    public Sicherheit<AufrufKontextImpl> getSicherheit() {
        return sicherheit;
    }

    public void setSicherheit(Sicherheit<AufrufKontextImpl> sicherheit) {
        this.sicherheit = sicherheit;
    }

    public AufrufKontextVerwalter<BenutzerverwaltungAufrufKontextImpl> getAufrufKontextVerwalter() {
        return aufrufKontextVerwalter;
    }

    public void setAufrufKontextVerwalter(
        AufrufKontextVerwalter<BenutzerverwaltungAufrufKontextImpl> aufrufKontextVerwalter) {
        this.aufrufKontextVerwalter = aufrufKontextVerwalter;
    }

    @Override
    protected Class<LoginModel> getMaskenModelKlasseZuController() {
        return LoginModel.class;
    }
}
