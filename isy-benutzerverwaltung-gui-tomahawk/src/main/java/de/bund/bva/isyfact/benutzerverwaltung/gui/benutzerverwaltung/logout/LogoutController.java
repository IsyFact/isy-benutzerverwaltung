package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.logout;

import org.springframework.stereotype.Controller;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.controller.AbstractBenutzerverwaltungController;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.EreignissSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.sicherheit.BenutzerverwaltungAufrufKontextImpl;
import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.bund.bva.isyfact.logging.LogKategorie;
import de.bund.bva.pliscommon.aufrufkontext.AufrufKontextVerwalter;

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
            getAwkWrapper().speichereAbmeldung(
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
