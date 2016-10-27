package de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.rolleanlegen;

import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.RolleModel;
import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;

/**
 * Model zum Anlegen von Rollen.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class RolleAnlegenModel extends AbstractMaskenModel {
    private static final long serialVersionUID = -6110443602902438569L;

    private RolleModel rolle = new RolleModel();

    public RolleModel getRolle() {
        return rolle;
    }

    public void setRolle(RolleModel rolle) {
        this.rolle = rolle;
    }
}
