package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzerpasswortzuruecksetzen;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.ValidierungSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.controller.AbstractBenutzerverwaltungController;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.HinweisSchluessel;
import de.bund.bva.isyfact.common.web.validation.ValidationMessage;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;

import java.util.Arrays;

/**
 * Controller zum Zurücksetzen des Passworts.
 *
 * @author msg systems ag, Björn Saxe
 */
public class BenutzerPasswortZuruecksetzenController
	extends AbstractBenutzerverwaltungController<BenutzerPasswortZuruecksetzenModel> {

    public void setzeBenutzer(BenutzerPasswortZuruecksetzenModel model, BenutzerModel benutzer) {
	model.setBenutzername(benutzer.getBenutzername());
    }

    public void setzePasswortZurueck(BenutzerPasswortZuruecksetzenModel model) {

	if (model.getPasswort().equals(model.getPasswortWiederholung())) {
	    try {
		getAwkWrapper().setzePasswortZurueck(model.getBenutzername(), model.getPasswort(),
			model.getPasswortWiederholung());

		getMessageController().writeSuccessMessage(
            MessageSourceHolder.getMessage(HinweisSchluessel.BENUTZER_PASSWORT_ZURUECKGESETZT));
        } catch (BenutzerverwaltungBusinessException exception) {
		zeigeNachricht(exception);
	    }
	} else {
	    getValidationController().processValidationMessages(Arrays.asList(new ValidationMessage("VA",
		    "neuesPasswortWiederholung", "neuesPasswortWiederholung",
		    MessageSourceHolder.getMessage(ValidierungSchluessel.MSG_PASSWORT_AENDERN_UNTERSCHIEDLICH))));
	}

    }

    @Override
    protected Class<BenutzerPasswortZuruecksetzenModel> getMaskenModelKlasseZuController() {
	return BenutzerPasswortZuruecksetzenModel.class;
    }

    /**
     * @see de.bund.bva.isyfact.common.web.global.RfGuiController#initialisiereModel(de.bund.bva.isyfact.common.web.global.AbstractMaskenModel)
     */
    @Override
    public void initialisiereModel(BenutzerPasswortZuruecksetzenModel model) {
    }
}
