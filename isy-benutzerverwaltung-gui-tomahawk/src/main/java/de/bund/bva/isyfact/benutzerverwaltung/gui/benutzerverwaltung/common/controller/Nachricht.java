package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.controller;

/**
 * Klasse zum Speichern von (Validierungs-)Nachrichten.
 *
 * @author Simon Spielmann, simon.spielmann@capgemini.com
 */
public class Nachricht {
    private Typ typ;

    private String text;

    public Nachricht(Typ typ, String text) {
        this.typ = typ;
        this.text = text;
    }

    public Typ getTyp() {
        return typ;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Nachricht [typ=" + typ + ", text=" + text + "]";
    }

    public enum Typ {
        FEHLER, WARNUNG, HINWEIS
    }

}
