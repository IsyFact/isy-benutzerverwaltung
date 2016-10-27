package de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung;

import java.io.Serializable;

/**
 * Bündelt alle Suchkriterien für Rollen.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class RolleSuchkriterien implements Serializable {

    private static final long serialVersionUID = 0L;

    private String id;

    private String name;

    /**
     * Initialisierung. Standardmäßig wird nicht gefiltert (=null).
     */
    public RolleSuchkriterien() {
        id = null;
        name = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
