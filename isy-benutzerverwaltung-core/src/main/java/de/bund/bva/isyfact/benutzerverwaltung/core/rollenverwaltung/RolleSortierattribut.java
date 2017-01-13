package de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung;

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
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.QRolle;

/**
 * Diese Klasse bietet die Möglichkeit, das Attribut der Rollen-Trefferliste zu
 * hinterlegen, nach dem die Sortierung stattfinden soll.
 *
 * @author Capgemini, Jonas Zitz
 * @version $Id: BenutzerSortierungNachAttribut.java 41637 2013-07-12 14:24:21Z jozitz $
 */
public enum RolleSortierattribut implements Sortierattribut {

    ID(QRolle.rolle.id), NAME(QRolle.rolle.name);

    private ComparableExpression<?> attribut;

    RolleSortierattribut(ComparableExpression<?> attribut) {
        this.attribut = attribut;
    }

    /**
     * Liefert das Default-Sortierattribut {@link RolleSortierattribut#ID}
     */
    public static RolleSortierattribut getStandard() {
        return ID;
    }

    @Override
    public ComparableExpression<?> getAttribut() {
        return attribut;
    }

}
