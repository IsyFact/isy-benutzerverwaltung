package de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.rollebearbeiten;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.HinweisSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.RolleModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.awkwrapper.RollenverwaltungAwkWrapper;
import de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.common.controller.AbstractRollenverwaltungController;
import de.bund.bva.isyfact.common.web.global.MessageController;
import de.bund.bva.pliscommon.sicherheit.Sicherheit;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.springframework.context.MessageSource;

import java.util.Objects;

/**
 * Controller zum Bearbeiten von Rollen.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class RolleBearbeitenController extends AbstractRollenverwaltungController<RolleBearbeitenModel> {

    private final Sicherheit<?> sicherheit;

    public RolleBearbeitenController(MessageController messageController, MessageSource messageSource,
        RollenverwaltungAwkWrapper awkWrapper, Sicherheit<?> sicherheit) {
        super(messageController, messageSource, awkWrapper);
        this.sicherheit = sicherheit;
    }

    @Override
    public void initialisiereModel(RolleBearbeitenModel model) {
    }

    /**
     * Diese Methode setzt die übergebene Rolle in das Model und initialisiert es.
     *
     * @param model das Model
     * @param rolle die übergebene Rolle
     */
    public void setzeRolle(RolleBearbeitenModel model, RolleModel rolle) {
        model.setRolle(rolle);
        model.setRollenIdVorVerarbeitung(rolle.getRollenId());
    }

    /**
     * Ändert die Rolle.
     *
     * @param model das Model
     */
    public void aendereRolle(RolleBearbeitenModel model) {
        try {
            RolleModel ergebnis =
                getAwkWrapper().aendereRolle(model.getRollenIdVorVerarbeitung(), model.getRolle());
            getMessageController().writeSuccessMessage(MessageSourceHolder
                .getMessage(HinweisSchluessel.ROLLE_GEAENDERT, ergebnis.getRollenId()));
            if (!Objects.equals(ergebnis.getRollenId(), model.getRollenIdVorVerarbeitung())) {
                // Bei Änderung der Rollen-ID müssen alle Benutzer neu geladen werden.
                sicherheit.leereCache();
            }
        } catch (BenutzerverwaltungValidationException validationException) {
            erzeugeNachrichten(validationException);
        }
    }

    @Override
    protected Class<RolleBearbeitenModel> getMaskenModelKlasseZuController() {
        return RolleBearbeitenModel.class;
    }

}
