package de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.rollesuchen;

import java.io.Serializable;

/**
 * Diese Klasse beinhaltet die Filterkriterien zur Suche nach Rollen.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class RolleSuchkriterienModel implements Serializable {

    private static final long serialVersionUID = 0L;

    private String id;

    private String name;

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
