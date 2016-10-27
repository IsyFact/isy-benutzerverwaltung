package de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Dieser Core-Datentyp speichert Rolleninformationen.
 *
 * @author Capgemini, Jonas Zitz
 * @version $Id: Rolle.java 41876 2013-07-26 08:30:23Z jozitz $
 */
public class RolleDaten {

    private String rollenId;

    private String rollenName;

    /**
     * Default Konstruktor.
     */
    public RolleDaten() {

    }

    /**
     * Der Konstruktur
     *
     * @param rollenId ist die RollenId
     */
    public RolleDaten(String rollenId) {
        this.rollenId = rollenId;
    }

    /**
     * Der Konstruktur
     *
     * @param rollenId   ist die RollenId
     * @param rollenName ist der RollenName
     */
    public RolleDaten(String rollenId, String rollenName) {
        this.rollenId = rollenId;
        this.rollenName = rollenName;
    }

    /**
     * This method gets the field <tt>rollenId</tt>.
     *
     * @return the field rollenId
     */
    @NotNull
    @Size(min = 1, message = "{validation.rolle.id.leer}")
    public String getRollenId() {
        return rollenId;
    }

    /**
     * This method sets the field <tt>rollenId</tt>.
     *
     * @param rollenId the new value of the field rollenId
     */
    public void setRollenId(String rollenId) {
        this.rollenId = rollenId;
    }

    /**
     * This method gets the field <tt>rollenName</tt>.
     *
     * @return the field rollenName
     */
    @NotNull
    @Size(min = 1, message = "{validation.rolle.name.leer}")
    public String getRollenName() {
        return rollenName;
    }

    /**
     * This method sets the field <tt>rollenName</tt>.
     *
     * @param rollenName the new value of the field rollenName
     */
    public void setRollenName(String rollenName) {
        this.rollenName = rollenName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "RollenId " + rollenId + ", RollenName: " + rollenName;
    }
}
