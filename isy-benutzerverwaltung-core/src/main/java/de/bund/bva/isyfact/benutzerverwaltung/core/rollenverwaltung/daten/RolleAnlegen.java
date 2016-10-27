package de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.daten;

import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.validation.UnbekannteRollenId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Schnittstellen-Objekt zum Anlegen einer Rolle.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class RolleAnlegen {

    private final String rollenId;

    private final String rollenName;

    /**
     * Erzeugt ein Schnittstellen-Objekt.
     *
     * @param rollenId   ID der Rolle
     * @param rollenName Name der Rolle
     */
    public RolleAnlegen(String rollenId, String rollenName) {
        this.rollenId = rollenId;
        this.rollenName = rollenName;
    }

    @UnbekannteRollenId
    @NotNull
    public String getRollenId() {
        return rollenId;
    }

    @NotNull
    @Size(min = 1, message = "{validation.rolle.name.leer}")
    public String getRollenName() {
        return rollenName;
    }

}
