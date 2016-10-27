package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzersuchen;

import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.RolleModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.SucheModel;

import java.util.Arrays;
import java.util.List;

/**
 * Trefferlisten Model für die Benutzer-Suche.
 *
 * @author Capgemini, Jonas Zitz
 */
public class BenutzerSuchenModel extends SucheModel<BenutzerModel, BenutzerSuchkriterienModel> {

    private static final long serialVersionUID = 1L;

    /**
     * Liste aller {@link BenutzerStatus} zur Anzeige in einer Auswahlliste.
     */
    private final List<BenutzerStatus> alleBenutzerStatus = Arrays.asList(BenutzerStatus.values());

    /**
     * Liste aller Rollen zur Anzeige in einer Auswahlliste.
     */
    private List<RolleModel> alleRollen;

    public List<BenutzerStatus> getAlleBenutzerStatus() {
        return alleBenutzerStatus;
    }

    public List<RolleModel> getAlleRollen() {
        return alleRollen;
    }

    public void setAlleRollen(List<RolleModel> alleRollen) {
        this.alleRollen = alleRollen;
    }

}
