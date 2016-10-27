package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.passwortzuruecksetzen;

import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.daten.PasswortZuruecksetzenDaten;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;
import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;

/**
 * Model zum Zurücksetzen von Passwörtern.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class PasswortZuruecksetzenModel extends AbstractMaskenModel {

    private static final long serialVersionUID = -6110443602902438569L;

    private BenutzerModel benutzer;

    private PasswortZuruecksetzenDaten passwortZuruecksetzen = new PasswortZuruecksetzenDaten();

    /**
     * This method gets the field <tt>benutzer</tt>.
     *
     * @return the field benutzer
     */
    public BenutzerModel getBenutzer() {
        return benutzer;
    }

    /**
     * Setzt das Model des Benutzers. Bereitet das Model für die Anzeige des Benutzers vor, indem es Felder
     * vor Auswahllisten o.ä. füllt.
     *
     * @param benutzer Model des Benutzers
     */
    public void setBenutzer(BenutzerModel benutzer) {
        this.benutzer = benutzer;
        passwortZuruecksetzen.setBenutzername(benutzer.getBenutzername());
    }

    public PasswortZuruecksetzenDaten getPasswortZuruecksetzen() {
        return passwortZuruecksetzen;
    }

}
