package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzerselbstbearbeiten;

import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.model.BenutzerKontaktdatenModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;

/**
 * Model zum Bearbeiten von Benutzern.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerSelbstBearbeitenModel extends BenutzerKontaktdatenModel {
    private static final long serialVersionUID = -6110443602902438569L;

    private String benutzername;

    /**
     * Setzt das Model des Benutzers. Bereitet das Model für die Anzeige des Benutzers vor, indem es Felder
     * vor Auswahllisten o.ä. füllt.
     *
     * @param benutzer Model des Benutzers
     */
    public void setBenutzer(BenutzerModel benutzer) {
        super.setBenutzer(benutzer);

        benutzername = benutzer.getBenutzername();
    }

    public String getBenutzername() {
        return benutzername;
    }

}
