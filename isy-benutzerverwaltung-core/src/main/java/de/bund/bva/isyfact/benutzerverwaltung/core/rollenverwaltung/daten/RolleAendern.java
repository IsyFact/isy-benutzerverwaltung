package de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.daten;

import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.validation.BekannteRollenId;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.validation.UnbekannteRollenId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Schnittstellen-Objekt zum Ändern einer Rolle.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class RolleAendern {

    private final String alteRollenId;

    private final String neueRollenId;

    private final String rollenName;

    /**
     * Erzeugt ein Schnittstellen-Objekt.
     *
     * @param alteRollenId alte ID der Rolle
     * @param neueRollenId neue ID der Rolle
     * @param rollenName   neuer Name der Rolle
     */
    public RolleAendern(String alteRollenId, String neueRollenId, String rollenName) {
        this.alteRollenId = alteRollenId;
        this.neueRollenId = neueRollenId;
        this.rollenName = rollenName;
    }

    @BekannteRollenId
    @NotNull
    public String getAlteRollenId() {
        return alteRollenId;
    }

    @UnbekannteRollenId
    public String getNeueRollenId() {
        return neueRollenId;
    }

    @NotNull
    @Size(min = 1, message = "{validation.rolle.name.leer}")
    public String getRollenName() {
        return rollenName;
    }

}
