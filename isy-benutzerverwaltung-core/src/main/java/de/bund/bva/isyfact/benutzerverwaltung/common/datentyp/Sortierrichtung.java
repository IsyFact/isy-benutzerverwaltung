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

/**
 * Die Klasse dient der Angabe der Sortierrichtung zu einem Attribut einer Trefferliste.
 * 
 * @author Capgemini, Jonas Zitz
 */
public enum Sortierrichtung {

    AUFSTEIGEND, ABSTEIGEND;

    /**
     * Liefert den Default-Wert zur Sortierung: {@link Sortierrichtung#AUFSTEIGEND}
     * @return Default-Wert zur Sortierung
     */
    public static Sortierrichtung getStandard() {
        return AUFSTEIGEND;
    }

    /**
     * @return {@link Boolean#TRUE}, wenn die Sortiernug als {@link Sortierrichtung#ABSTEIGEND} gesetzt ist.
     *         {@link Boolean#FALSE} ansonsten.
     */
    public Boolean isAbsteigend() {
        return this == ABSTEIGEND;
    }

    /**
     * @return {@link Boolean#TRUE}, wenn die Sortiernug als {@link Sortierrichtung#AUFSTEIGEND} gesetzt ist.
     *         {@link Boolean#FALSE} ansonsten.
     */
    public Boolean isAufsteigend() {
        return this == AUFSTEIGEND;
    }

}
