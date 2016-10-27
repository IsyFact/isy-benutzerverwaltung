package de.bund.bva.isyfact.benutzerverwaltung.gui.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * GUI-Model für ein Ergebnis einer Suche des Anwendungskerns.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class SuchergebnisModel<T> {

    private List<T> trefferliste = new ArrayList<>();

    private long anzahlTreffer;

    public List<T> getTrefferliste() {
        return trefferliste;
    }

    public void setTrefferliste(List<T> trefferliste) {
        this.trefferliste = trefferliste;
    }

    public long getAnzahlTreffer() {
        return anzahlTreffer;
    }

    public void setAnzahlTreffer(long anzahlTreffer) {
        this.anzahlTreffer = anzahlTreffer;
    }
}
