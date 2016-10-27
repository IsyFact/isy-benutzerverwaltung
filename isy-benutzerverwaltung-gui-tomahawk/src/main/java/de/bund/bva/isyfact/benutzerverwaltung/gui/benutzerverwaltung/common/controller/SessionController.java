package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.controller;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Required;

import de.bund.bva.pliscommon.aufrufkontext.AufrufKontext;
import de.bund.bva.pliscommon.aufrufkontext.AufrufKontextVerwalter;

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
