package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzerbearbeiten;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.RolleModel;
import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;

/**
 * Model zum Bearbeiten von Benutzern.
 *
 * @author Capgemini, Jonas Zitz
 * @version $Id: BenutzerAnlegenDaten.java 41863 2013-07-25 06:55:46Z jozitz $
 */
public class BenutzerBearbeitenModel extends AbstractMaskenModel {

    private static final long serialVersionUID = -6110443602902438569L;
    
    private boolean benutzerBearbeitetSichSelbst = false;

    /**
     * Liste aller {@link BenutzerStatus} zur Anzeige in einer Auswahlliste.
     */
    private final List<BenutzerStatus> alleBenutzerStatus = Arrays.asList(BenutzerStatus.values());

    private BenutzerModel benutzer;
    private String alterBenutzername;

    /**
     * Liste aller Rollen zur Anzeige in einer Auswahlliste.
     */
    private List<RolleModel> alleRollen;

    /**
     * Selektierte Rollen, zur Anzeige der ausgewählten Rollen in der Auswahlliste.
     */
    private List<String> selektierteRollenIds = new ArrayList<>();

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
        alterBenutzername = benutzer.getBenutzername();

        if (benutzer.getRollen() != null) {
           selektierteRollenIds = benutzer.getRollen().stream().map(RolleModel::getRollenId).collect(Collectors.toList());
        }
    }

    public List<RolleModel> getAlleRollen() {
        return alleRollen;
    }

    public void setAlleRollen(List<RolleModel> alleRollen) {
        this.alleRollen = alleRollen;
    }

    public List<String> getSelektierteRollenIds() {
        return selektierteRollenIds;
    }

    public void setSelektierteRollenIds(List<String> selektierteRollenIds) {
        this.selektierteRollenIds = selektierteRollenIds;
    }

    public List<BenutzerStatus> getAlleBenutzerStatus() {
        return alleBenutzerStatus;
    }

    public boolean isBenutzerBearbeitetSichSelbst() {
	return benutzerBearbeitetSichSelbst;
    }

    public void setBenutzerBearbeitetSichSelbst(boolean benutzerBearbeitetSichSelbst) {
	this.benutzerBearbeitetSichSelbst = benutzerBearbeitetSichSelbst;
    }

    public String getAlterBenutzername() {
	return alterBenutzername;
    }

    public void setAlterBenutzername(String alterBenutzername) {
	this.alterBenutzername = alterBenutzername;
    }

}
