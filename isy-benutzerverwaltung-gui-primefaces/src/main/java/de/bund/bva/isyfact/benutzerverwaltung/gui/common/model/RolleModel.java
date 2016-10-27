package de.bund.bva.isyfact.benutzerverwaltung.gui.common.model;

import de.bund.bva.pliscommon.sicherheit.Rolle;

import java.io.Serializable;
import java.util.Objects;

/**
 * Dieser Datentyp speichert {@link Rolle Rolleninformationen} für die GUI.
 *
 * @author Capgemini, Jonas Zitz
 * @version $Id: RolleTreffer.java 41876 2013-07-26 08:30:23Z jozitz $
 */
public class RolleModel implements Serializable {

    /**
     * The field <tt>serialVersionUID</tt>
     */
    private static final long serialVersionUID = 1L;

    private String rollenId;

    private String rollenName;

    /**
     * Default Konstruktor.
     */
    public RolleModel() {
        super();
    }

    /**
     * Der Konstruktur
     *
     * @param rollenId ist die RollenId
     */
    public RolleModel(String rollenId) {
        this.rollenId = rollenId;
    }

    /**
     * Der Konstruktur
     *
     * @param rollenId   ist die RollenId
     * @param rollenName ist der RollenName
     */
    public RolleModel(String rollenId, String rollenName) {
        this.rollenId = rollenId;
        this.rollenName = rollenName;
    }

    /**
     * This method gets the field <tt>rollenId</tt>.
     *
     * @return the field rollenId
     */
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

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RolleModel && Objects.equals(rollenId, ((RolleModel) obj).getRollenId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rollenId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return rollenId + " (" + rollenName + ")";
    }
}
