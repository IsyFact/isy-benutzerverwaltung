package de.bund.bva.isyfact.benutzerverwaltung.gui.common.model;

/*-
 * #%L
 * IsyFact Benutzerverwaltung GUI mit Primefaces
 * %%
 * Copyright (C) 2016 - 2017 Bundesverwaltungsamt (BVA)
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierrichtung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Abstraktes Model für DataTables aus Primefaces mit Lazy Loading.
 *
 * @param <T> Typ des Treffers
 * @param <K> Typ der Suchkriterien
 * @author msg systems ag, Stefan Dellmuth
 */
public abstract class TrefferlisteModel<T, K> extends LazyDataModel<T> {

    private K suchkriterien;

    private BenutzerverwaltungValidationException exception;

    public K getSuchkriterien() {
        return suchkriterien;
    }

    public void setSuchkriterien(K suchkriterien) {
        this.suchkriterien = suchkriterien;
    }

    public BenutzerverwaltungValidationException getException() {
        return exception;
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder,
        Map<String, Object> filters) {
        Sortierung sortierung =
            new Sortierung(ermittleSortierattribut(sortField), ermittleSortierrichtung(sortOrder));
        Paginierung paginierung = new Paginierung(first, pageSize);

        try {
            SuchergebnisModel<T> suchergebnis = suche(suchkriterien, sortierung, paginierung);
            setRowCount((int) suchergebnis.getAnzahlTreffer());
            exception = null;
            return suchergebnis.getTrefferliste();
        } catch (BenutzerverwaltungValidationException e) {
            setRowCount(0);
            exception = e;
            return new ArrayList<>();
        }
    }

    /**
     * Sucht anhand der angegebenen Suchkriterien, Sortierung und Paginierung im Anwendungskern nach Treffern.
     *
     * @param suchkriterien Suchkriterien
     * @param sortierung    Sortierung der Trefferliste
     * @param paginierung   Umfang der Trefferliste
     * @return das Model des Suchergebnisses.
     * @throws BenutzerverwaltungValidationException wenn die Eingabedaten fehlerhaft sind.
     */
    protected abstract SuchergebnisModel<T> suche(K suchkriterien, Sortierung sortierung,
        Paginierung paginierung) throws BenutzerverwaltungValidationException;

    @Override
    public abstract T getRowData(String rowKey);

    @Override
    public abstract Object getRowKey(T object);

    protected abstract Sortierattribut ermittleSortierattribut(String sortierAttribut);

    private Sortierrichtung ermittleSortierrichtung(SortOrder sortOrder) {
        if (sortOrder == null) {
            return Sortierrichtung.getStandard();
        } else if (sortOrder.equals(SortOrder.ASCENDING)) {
            return Sortierrichtung.AUFSTEIGEND;
        }
        return Sortierrichtung.ABSTEIGEND;
    }

}
