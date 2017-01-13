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
