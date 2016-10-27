package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzeranlegen;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.BenutzerverwaltungAwkWrapper;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.daten.BenutzerAnlegenDaten;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.controller.AbstractBenutzerverwaltungController;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.HinweisSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;
import de.bund.bva.isyfact.common.web.global.MessageController;
import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.springframework.context.MessageSource;

/**
 * Controller zum Anlegen von Benutzern.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerAnlegenController extends AbstractBenutzerverwaltungController<BenutzerAnlegenModel> {

    private static final IsyLogger LOG = IsyLoggerFactory.getLogger(BenutzerAnlegenController.class);

    public BenutzerAnlegenController(MessageController messageController, MessageSource messageSource,
        BenutzerverwaltungAwkWrapper awkWrapper) {
        super(messageController, messageSource, awkWrapper);
    }

    @Override
    public void initialisiereModel(BenutzerAnlegenModel model) {
        try {
            model.setAlleRollen(getAwkWrapper().leseAlleRollen());
        } catch (BenutzerverwaltungValidationException e) {
            erzeugeNachrichten(e);
        }
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
            erzeugeNachrichten(validationException);
        }
    }

    @Override
    protected Class<BenutzerAnlegenModel> getMaskenModelKlasseZuController() {
        return BenutzerAnlegenModel.class;
    }

}
