package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.rollenzuweisung;

import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.RolleModel;
import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Dieses Model stellt die Daten zur Rollenzuweisung-Maske bereit.
 *
 * @author Capgemini, Jonas Zitz
 * @author msg systems ag, Stefan Dellmuth
 */
public class RollenZuweisungModel extends AbstractMaskenModel {
    private static final long serialVersionUID = 1L;

    /**
     * Liste aller Rollen zur Anzeige in einer Auswahlliste.
     */
    private List<RolleModel> alleRollen;

    /**
     * Selektierte Rollen, zur Anzeige der ausgewählten Rollen in der Auswahlliste.
     */
    private String selektierteRollenId;

    /**
     * Liste aller Benutzer zur Anzeige in einer Auswahlliste.
     */
    private List<BenutzerModel> alleBenutzer = new ArrayList<>();

    /**
     * Selektierte Benutzernamen für die selektierte Rolle.
     */
    private List<String> benutzerZuRolle = new ArrayList<>();

    public List<RolleModel> getAlleRollen() {
        return alleRollen;
    }

    public void setAlleRollen(List<RolleModel> alleRollen) {
        this.alleRollen = alleRollen;
    }

    public String getSelektierteRollenId() {
        return selektierteRollenId;
    }

    public void setSelektierteRollenId(String selektierteRollenId) {
        this.selektierteRollenId = selektierteRollenId;
    }

    public List<BenutzerModel> getAlleBenutzer() {
        return alleBenutzer;
    }

    public void setAlleBenutzer(List<BenutzerModel> alleBenutzer) {
        this.alleBenutzer = alleBenutzer;
    }

    public List<String> getBenutzerZuRolle() {
        return benutzerZuRolle;
    }

    public void setBenutzerZuRolle(List<String> benutzerZuRolle) {
        this.benutzerZuRolle = benutzerZuRolle;
    }

}
