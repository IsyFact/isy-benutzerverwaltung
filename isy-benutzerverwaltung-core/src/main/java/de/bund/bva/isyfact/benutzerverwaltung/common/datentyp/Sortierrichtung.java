package de.bund.bva.isyfact.benutzerverwaltung.common.datentyp;

/**
 * Die Klasse dient der Angabe der Sortierrichtung zu einem Attribut einer Trefferliste.
 * 
 * @author Capgemini, Jonas Zitz
 */
public enum Sortierrichtung {

    AUFSTEIGEND, ABSTEIGEND;

    /**
     * Liefert den Default-Wert zur Sortierung: {@link Sortierrichtung#AUFSTEIGEND}
     * @return Default-Wert zur Sortierung
     */
    public static Sortierrichtung getStandard() {
        return AUFSTEIGEND;
    }

    /**
     * @return {@link Boolean#TRUE}, wenn die Sortiernug als {@link Sortierrichtung#ABSTEIGEND} gesetzt ist.
     *         {@link Boolean#FALSE} ansonsten.
     */
    public Boolean isAbsteigend() {
        return this == ABSTEIGEND;
    }

    /**
     * @return {@link Boolean#TRUE}, wenn die Sortiernug als {@link Sortierrichtung#AUFSTEIGEND} gesetzt ist.
     *         {@link Boolean#FALSE} ansonsten.
     */
    public Boolean isAufsteigend() {
        return this == AUFSTEIGEND;
    }

}
