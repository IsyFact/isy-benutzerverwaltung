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
