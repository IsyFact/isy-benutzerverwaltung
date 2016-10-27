package de.bund.bva.isyfact.benutzerverwaltung.common.datentyp;

import com.querydsl.core.types.dsl.ComparableExpression;

import java.io.Serializable;

/**
 * Diese Klasse steuert die Sortierung von Suchanfragen.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class Sortierung implements Serializable {

    private static final long serialVersionUID = 1L;

    private ComparableExpression<?> attribut;

    private Sortierrichtung richtung;

    /**
     * Erzeugt eine neue Sortierung.
     *
     * @param sortierattribut das Attribut, nach dem sortiert wird
     * @param richtung die {@link Sortierrichtung}
     */
    public Sortierung(Sortierattribut sortierattribut, Sortierrichtung richtung) {
        attribut = sortierattribut.getAttribut();
        this.richtung = richtung;
    }

    public ComparableExpression<?> getAttribut() {
        return attribut;
    }

    public void setAttribut(ComparableExpression<?> attribut) {
        this.attribut = attribut;
    }

    public Sortierrichtung getRichtung() {
        return richtung;
    }

    public void setRichtung(Sortierrichtung richtung) {
        this.richtung = richtung;
    }

}
