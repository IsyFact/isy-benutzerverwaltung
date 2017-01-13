package de.bund.bva.isyfact.benutzerverwaltung.common.datentyp;

/*-
 * #%L
 * IsyFact Benutzerverwaltung Core
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
