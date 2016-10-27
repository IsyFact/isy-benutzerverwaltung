package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzerpasswortaendern;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.ValidierungSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.controller.AbstractBenutzerverwaltungController;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.HinweisSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.sicherheit.BenutzerverwaltungAufrufKontextImpl;
import de.bund.bva.isyfact.common.web.validation.ValidationMessage;
import de.bund.bva.pliscommon.aufrufkontext.AufrufKontextVerwalter;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.springframework.beans.factory.annotation.Required;

import java.util.Arrays;

/**
 * Controller zum Ändern des eigenen Passworts.
 *
 * @author msg systems ag, Björn Saxe
 */
public class BenutzerPasswortAendernController
	extends AbstractBenutzerverwaltungController<BenutzerPasswortAendernModel> {

    AufrufKontextVerwalter<BenutzerverwaltungAufrufKontextImpl> aufrufKontextVerwalter;

    public void setzePasswort(BenutzerPasswortAendernModel model) {

	if (model.getNeuesPasswort().equals(model.getNeuesPasswortWiederholung())) {
	    String benutzername = getAufrufKontextVerwalter().getAufrufKontext().getDurchfuehrenderBenutzerKennung();

	    try {
		getAwkWrapper().setzePasswort(benutzername, model.getAltesPasswort(), model.getNeuesPasswort(),
			model.getNeuesPasswortWiederholung());

            getMessageController().writeSuccessMessage(
                MessageSourceHolder.getMessage(HinweisSchluessel.BENUTZER_AKTUALISIERT, benutzername));

	    } catch (BenutzerverwaltungBusinessException exception) {
		zeigeNachricht(exception);
	    }
	} else {
	    getValidationController().processValidationMessages(
		    Arrays.asList(new ValidationMessage("VA", "neuesPasswortWiederholung", "neuesPasswortWiederholung",
			    MessageSourceHolder.getMessage(ValidierungSchluessel.MSG_PASSWORT_AENDERN_UNTERSCHIEDLICH))));
	}
    }

    @Override
    protected Class<BenutzerPasswortAendernModel> getMaskenModelKlasseZuController() {
	return BenutzerPasswortAendernModel.class;
    }

    /**
     * @see de.bund.bva.isyfact.common.web.global.RfGuiController#initialisiereModel(de.bund.bva.isyfact.common.web.global.AbstractMaskenModel)
     */
    @Override
    public void initialisiereModel(BenutzerPasswortAendernModel model) {
    }

    public AufrufKontextVerwalter<BenutzerverwaltungAufrufKontextImpl> getAufrufKontextVerwalter() {
	return aufrufKontextVerwalter;
    }

    @Required
    public void setAufrufKontextVerwalter(
	    AufrufKontextVerwalter<BenutzerverwaltungAufrufKontextImpl> aufrufKontextVerwalter) {
	this.aufrufKontextVerwalter = aufrufKontextVerwalter;
    }
}
