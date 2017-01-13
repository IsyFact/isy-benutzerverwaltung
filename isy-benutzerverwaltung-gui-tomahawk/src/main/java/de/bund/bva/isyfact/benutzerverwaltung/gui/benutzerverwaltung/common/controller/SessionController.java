package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.controller;

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

import de.bund.bva.pliscommon.aufrufkontext.AufrufKontext;
import de.bund.bva.pliscommon.aufrufkontext.AufrufKontextVerwalter;
import org.springframework.beans.factory.annotation.Required;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * Bietet Zugriff auf Session-Informationen, die auf allen Seiten benoetigt werden, bspw. via des Templates.
 *
 * @author Capgemini, Jonas Zitz
 */
public class SessionController {

    private AufrufKontextVerwalter<AufrufKontext> aufrufKontextVerwalter;

    private String logoutUrl;

    public String getDurchfuehrenderSachbearbeiterNameDesAngemeldetenBenutzers() {
        return aufrufKontextVerwalter.getAufrufKontext().getDurchfuehrenderSachbearbeiterName();
    }

    public String getBenutzernameDesAngemeldetenBenutzers() {
        return aufrufKontextVerwalter.getAufrufKontext().getDurchfuehrenderBenutzerKennung();
    }

    /**
     * Diese Methode leitet einen Benutzer zum Logout-Handler, der den Spring-Logout durchfuehrt.
     *
     * @throws IOException
     */
    public void logout() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.redirect(externalContext.getRequestContextPath() + logoutUrl);
        facesContext.responseComplete();
    }

    @Required
    public void setAufrufKontextVerwalter(AufrufKontextVerwalter<AufrufKontext> aufrufKontextVerwalter) {
        this.aufrufKontextVerwalter = aufrufKontextVerwalter;
    }

    @Required
    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

}
