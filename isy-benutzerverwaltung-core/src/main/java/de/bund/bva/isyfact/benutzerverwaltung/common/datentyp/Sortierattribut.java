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

/**
 * Stellt ein Attribut zur Sortierung bereit. Die Schnittstelle kann z.B. durch Enums implementiert werden,
 * die dann wiederum Attribute von Query-Typen (von QueryDSL) zurückgeben.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public interface Sortierattribut {

    /**
     * Gibt das Attribut des Query-Typen zurück.
     *
     * @return das Attribut des Query-Typen.
     */
    ComparableExpression<?> getAttribut();

}
