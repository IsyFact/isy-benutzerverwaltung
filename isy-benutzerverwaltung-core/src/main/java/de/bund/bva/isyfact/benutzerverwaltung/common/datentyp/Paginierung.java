package de.bund.bva.isyfact.benutzerverwaltung.common.datentyp;

import java.io.Serializable;

/**
 * DTO zur Angabe von Paginierungsinformationen zur Eingrenzung eines Suchergebnisses.
 *
 * @author Capgemini, Jonas Zitz
 */
public class Paginierung implements Serializable {

    /**
     * ID
     */
    private static final long serialVersionUID = 1L;

    private int ersterTreffer;

    private int trefferProSeite;

    /**
     * Konstruktur.
     *
     * @param ersterTreffer   ist der Treffer, bei der die Suchergebnisse starten sollen (erster Treffer = 0)
     * @param trefferProSeite ist die Anzahl an Treffern, die geliefert werden soll.
     */
    public Paginierung(int ersterTreffer, int trefferProSeite) {
        this.ersterTreffer = ersterTreffer;
        this.trefferProSeite = trefferProSeite;
    }

    public int getErsterTreffer() {
        return ersterTreffer;
    }

    /**
     * @param ersterTreffer ist der Treffer, bei der die Suchergebnisse starten sollen (erster Treffer = 0)
     */
    public void setErsterTreffer(int ersterTreffer) {
        this.ersterTreffer = ersterTreffer;
    }

    public int getTrefferProSeite() {
        return trefferProSeite;
    }

    public void setTrefferProSeite(int trefferProSeite) {
        this.trefferProSeite = trefferProSeite;
    }

}
