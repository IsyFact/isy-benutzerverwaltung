package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzeranlegen;

import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.daten.BenutzerAnlegenDaten;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.RolleModel;
import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;

import java.util.Arrays;
import java.util.List;

/**
 * Model zum Anlegen von Benutzern.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerAnlegenModel extends AbstractMaskenModel {
    private static final long serialVersionUID = -6110443602902438569L;

    /**
     * Liste aller {@link BenutzerStatus} zur Anzeige in einer Auswahlliste.
     */
    private final List<BenutzerStatus> alleBenutzerStatus = Arrays.asList(BenutzerStatus.values());

    private BenutzerAnlegenDaten benutzer = new BenutzerAnlegenDaten();

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

    public BenutzerAnlegenDaten getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(BenutzerAnlegenDaten benutzer) {
        this.benutzer = benutzer;
    }

}
