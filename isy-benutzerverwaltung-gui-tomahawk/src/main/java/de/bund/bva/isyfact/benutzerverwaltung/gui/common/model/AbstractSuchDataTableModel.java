package de.bund.bva.isyfact.benutzerverwaltung.gui.common.model;

/*-
 * #%L
 * IsyFact Benutzerverwaltung GUI mit Tomahawk
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

import de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableItem;
import de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableModel;

/**
 * Abstrakte Basisklasse für alle Model die Suchen mit einer plis-web DataTable
 * durchführen.
 *
 * @param <R>
 *     Der Typ des Items in der DataTable.
 * @param <K>
 *     Der Typ des Suchkriterien-Models.
 * @author msg systems ag, Björn Saxe
 */
public abstract class AbstractSuchDataTableModel<R extends DataTableItem, K> extends DataTableModel<R> {
    private static final long serialVersionUID = 1L;

    private K suchkriterien;

    private R ausgewaehlterTreffer;

    public K getSuchkriterien() {
        return suchkriterien;
    }

    public void setSuchkriterien(K suchkriterien) {
        this.suchkriterien = suchkriterien;
    }

    public R getAusgewaehlterTreffer() {
        return ausgewaehlterTreffer;
    }

    /**
     * Speichert die Auswahl eines einzelnen Items unabhängig vom
     * <code>SelectionModel</code> des DataTableModels. Zum Beispiel für die
     * Verwendung Action-Buttons in der Zeile des Items die über
     * <code>f:setPropertyActionListener</code> des jeweilige ausgewählte Item
     * setzen.
     *
     * @param ausgewaehlterTreffer
     *     Das in der DataTable ausgewählte Item.
     */
    public void setAusgewaehlterTreffer(R ausgewaehlterTreffer) {
        this.ausgewaehlterTreffer = ausgewaehlterTreffer;
    }
}
