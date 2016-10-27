package de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.rollebearbeiten;

import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.RolleModel;
import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;

/**
 * Model zum Bearbeiten von Rollen.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class RolleBearbeitenModel extends AbstractMaskenModel {

    private static final long serialVersionUID = -6110443602902438569L;

    private RolleModel rolle;

    private String rollenIdVorVerarbeitung;

    public RolleModel getRolle() {
        return rolle;
    }

    public void setRolle(RolleModel rolle) {
        this.rolle = rolle;
    }

    public String getRollenIdVorVerarbeitung() {
        return rollenIdVorVerarbeitung;
    }

    public void setRollenIdVorVerarbeitung(String rollenIdVorVerarbeitung) {
        this.rollenIdVorVerarbeitung = rollenIdVorVerarbeitung;
    }
}
