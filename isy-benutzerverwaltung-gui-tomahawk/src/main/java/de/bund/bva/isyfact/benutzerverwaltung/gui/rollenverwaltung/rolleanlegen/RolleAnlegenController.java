package de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.rolleanlegen;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.controller.AbstractBenutzerverwaltungController;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.RolleModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.HinweisSchluessel;
import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;

/**
 * Controller zum Anlegen von Rollen.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class RolleAnlegenController extends AbstractBenutzerverwaltungController<RolleAnlegenModel> {

    private static final IsyLogger LOG = IsyLoggerFactory.getLogger(RolleAnlegenController.class);

    @Override
    public void initialisiereModel(RolleAnlegenModel model) {
    }

    /**
     * Legt den Benutzer über den AWK-Wrapper an.
     */
    public void legeRolleAn(RolleAnlegenModel model) {
        try {
            RolleModel rolleModel = getAwkWrapper().legeRolleAn(model.getRolle());
            model.setRolle(new RolleModel());

            String nachricht = MessageSourceHolder
                .getMessage(HinweisSchluessel.ROLLE_ERSTELLT, rolleModel.getRollenId());
            getMessageController().writeSuccessMessage(nachricht);
            LOG.debugFachdaten(nachricht);
        } catch (BenutzerverwaltungValidationException validationException) {
            zeigeNachricht(validationException);
        }
    }

    @Override
    protected Class<RolleAnlegenModel> getMaskenModelKlasseZuController() {
        return RolleAnlegenModel.class;
    }

}
