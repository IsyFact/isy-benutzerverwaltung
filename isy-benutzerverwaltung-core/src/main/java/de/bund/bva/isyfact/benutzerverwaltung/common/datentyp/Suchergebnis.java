package de.bund.bva.isyfact.benutzerverwaltung.common.datentyp;

import java.util.List;

/**
 * Beschreibt ein Suchergebnis. Enthält die Trefferliste sowie die Anzahl aller Treffer. Die Anzahl aller
 * Treffer kann durch die Verwendung von Paginierung größer als die Länge der Trefferliste sein.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class Suchergebnis<T> {

    private final List<T> trefferliste;

    private final long anzahlTreffer;

    public Suchergebnis(List<T> trefferliste, long anzahlTreffer) {
        this.trefferliste = trefferliste;
        this.anzahlTreffer = anzahlTreffer;
    }

    public List<T> getTrefferliste() {
        return trefferliste;
    }

    public long getAnzahlTreffer() {
        return anzahlTreffer;
    }

}
