package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzerbearbeiten;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.daten.BenutzerBearbeitenSelbst;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.controller.AbstractBenutzerverwaltungController;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.HinweisSchluessel;
import de.bund.bva.pliscommon.aufrufkontext.AufrufKontext;
import de.bund.bva.pliscommon.aufrufkontext.AufrufKontextVerwalter;
import de.bund.bva.pliscommon.sicherheit.Sicherheit;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.springframework.beans.factory.annotation.Required;

import java.io.IOException;

/**
 * Controller zum Bearbeiten von Benutzern.
 *
 * @author Capgemini, Jonas Zitz
 * @version $Id: BenutzerBearbeitenController.java 41870 2013-07-25 13:54:34Z
 *          jozitz $
 */
public class BenutzerBearbeitenController extends AbstractBenutzerverwaltungController<BenutzerBearbeitenModel> {

    private AufrufKontextVerwalter<?> aufrufKontextVerwalter;

    private Sicherheit<?> sicherheit;

    @Override
    public void initialisiereModel(BenutzerBearbeitenModel model) {
	if (model.isBenutzerBearbeitetSichSelbst()) {
	    String benutzername = aufrufKontextVerwalter.getAufrufKontext().getDurchfuehrenderBenutzerKennung();

	    try {
		model.setBenutzer(getAwkWrapper().leseBenutzer(benutzername));
	    } catch (BenutzerverwaltungBusinessException validationException) {
		getMessageController().writeException(validationException);
	    }
	}
	model.setAlleRollen(getAwkWrapper().getRollen());
    }

    /**
     * Diese Methode setzt den uebergebenen {@link BenutzerModel} in das Model
     * und initialisiert es.
     *
     * @param model
     *            das Model
     * @param benutzer
     *            der uebergebene {@link BenutzerModel}
     */
    public void setzeBenutzer(BenutzerBearbeitenModel model, BenutzerModel benutzer) {
	model.setBenutzer(benutzer);
    }

    /**
     * Funktion zum Bearbeiten eines Benutzers
     *
     * @param model
     *            das {@link BenutzerBearbeitenModel}
     * @throws IOException
     */
    public void benutzerBearbeiten(BenutzerBearbeitenModel model) throws IOException {
	try {
	    BenutzerModel ergebnis = getAwkWrapper().aendereBenutzer(model);
	    // Das Leeren des Caches zwingt alle Benutzer intern zur erneuten
	    // Anmeldung.
	    // Der gelöschte Benutzer stößt auf einen Fehler und ist so nicht
	    // mehr angemeldet.
	    sicherheit.leereCache();

	    // Wenn der Benutzer seinen eigenen Namen geändert hat, muss der
	    // Aufrufkontext geändert werden.
	    // Außerdem muss sich der Benutzer neu autorisieren, falls sich auch
	    // die Rollen geändert haben.
	    AufrufKontext angemeldeterBenutzer = aufrufKontextVerwalter.getAufrufKontext();
	    if (angemeldeterBenutzer.getDurchfuehrenderBenutzerKennung().equals(model.getAlterBenutzername())) {
		angemeldeterBenutzer.setDurchfuehrenderBenutzerKennung(model.getBenutzer().getBenutzername());
		angemeldeterBenutzer.setRollenErmittelt(false);
	    }
	    getMessageController().writeSuccessMessage(MessageSourceHolder
		    .getMessage(HinweisSchluessel.BENUTZER_AKTUALISIERT, ergebnis.getBenutzername()));
	} catch (BenutzerverwaltungBusinessException validationException) {
	    zeigeNachricht(validationException);
	}
    }

    /**
     * Funktion zur Selbstbearbeitung durch den Benutzer.
     *
     * @param model
     *            das {@link BenutzerBearbeitenModel}
     */
    public void benutzerBearbeitenSelbst(BenutzerBearbeitenModel model) {
	try {
	    BenutzerBearbeitenSelbst bearbeitenSelbst = new BenutzerBearbeitenSelbst();
	    bearbeitenSelbst.setBehoerde(model.getBenutzer().getBehoerde());
	    bearbeitenSelbst.setBenutzername(model.getBenutzer().getBenutzername());
	    bearbeitenSelbst.setEmailAdresse(model.getBenutzer().getEmailAdresse());
	    bearbeitenSelbst.setNachname(model.getBenutzer().getNachname());
	    bearbeitenSelbst.setTelefonnummer(model.getBenutzer().getTelefonnummer());
	    bearbeitenSelbst.setVorname(model.getBenutzer().getVorname());

	    getAwkWrapper().aendereBenutzerSelbst(bearbeitenSelbst);

	    getMessageController().writeSuccessMessage(MessageSourceHolder
		    .getMessage(HinweisSchluessel.BENUTZER_AKTUALISIERT, model.getBenutzer().getBenutzername()));
	} catch (BenutzerverwaltungBusinessException validationException) {
	    zeigeNachricht(validationException);
	}
    }

    @Required
    public void setAufrufKontextVerwalter(AufrufKontextVerwalter<?> aufrufKontextVerwalter) {
	this.aufrufKontextVerwalter = aufrufKontextVerwalter;
    }

    @Override
    protected Class<BenutzerBearbeitenModel> getMaskenModelKlasseZuController() {
	return BenutzerBearbeitenModel.class;
    }

    @Required
    public void setSicherheit(Sicherheit<?> sicherheit) {
	this.sicherheit = sicherheit;
    }
}
