package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzeranlegen;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.daten.BenutzerAnlegenDaten;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.controller.AbstractBenutzerverwaltungController;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.HinweisSchluessel;
import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;

/**
 * Controller zum Anlegen von Benutzern.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerAnlegenController extends AbstractBenutzerverwaltungController<BenutzerAnlegenModel> {

    private static final IsyLogger LOG = IsyLoggerFactory.getLogger(BenutzerAnlegenController.class);

    @Override
    public void initialisiereModel(BenutzerAnlegenModel model) {
        model.setAlleRollen(getAwkWrapper().getRollen());
    }

    /**
     * Legt den Benutzer über den AWK-Wrapper an.
     */
    public void legeBenutzerAn(BenutzerAnlegenModel model) {
        try {
            BenutzerModel benutzerDaten = getAwkWrapper().legeBenutzerAn(model.getBenutzer());
            model.setBenutzer(new BenutzerAnlegenDaten());

            String nachricht = MessageSourceHolder
                .getMessage(HinweisSchluessel.BENUTZER_ERSTELLT, benutzerDaten.getBenutzername());
            getMessageController().writeSuccessMessage(nachricht);
            LOG.debugFachdaten(nachricht);
        } catch (BenutzerverwaltungBusinessException validationException) {
            zeigeNachricht(validationException);
        }
    }

    @Override
    protected Class<BenutzerAnlegenModel> getMaskenModelKlasseZuController() {
        return BenutzerAnlegenModel.class;
    }

}