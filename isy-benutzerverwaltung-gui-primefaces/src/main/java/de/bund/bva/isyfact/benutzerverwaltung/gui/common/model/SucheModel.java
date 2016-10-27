package de.bund.bva.isyfact.benutzerverwaltung.gui.common.model;

import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;

/**
 * Model für einen Flow, der eine Suche implementiert.
 *
 * @param <T> Typ des Treffers
 * @param <K> Typ der Suchkriterien
 * @author msg systems ag, Stefan Dellmuth
 */
public abstract class SucheModel<T, K> extends AbstractMaskenModel {

    private static final long serialVersionUID = 1L;

    private TrefferlisteModel<T, K> trefferliste;

    private T ausgewaehlterTreffer;

    public TrefferlisteModel<T, K> getTrefferliste() {
        return trefferliste;
    }

    public void setTrefferliste(TrefferlisteModel<T, K> trefferliste) {
        this.trefferliste = trefferliste;
    }

    public T getAusgewaehlterTreffer() {
        return ausgewaehlterTreffer;
    }

    public void setAusgewaehlterTreffer(T ausgewaehlterTreffer) {
        this.ausgewaehlterTreffer = ausgewaehlterTreffer;
    }
}
