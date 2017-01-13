package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung;

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
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;

import static de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.QBenutzer.benutzer;

/**
 * Diese Klasse bietet die Moeglichkeit, das Attribut der {@link BenutzerDaten Benutzer-Trefferliste} zu
 * hinterlegen, nach dem die Sortierung stattfinden soll.
 *
 * @author Capgemini, Jonas Zitz
 * @version $Id: BenutzerSortierungNachAttribut.java 41637 2013-07-12 14:24:21Z jozitz $
 */
public enum BenutzerSortierattribut implements Sortierattribut {

    NACHNAME(benutzer.nachname), VORNAME(benutzer.vorname), BEHOERDE(benutzer.behoerde), BENUTZERNAME(
        benutzer.benutzername);

    private ComparableExpression<?> attribut;

    BenutzerSortierattribut(ComparableExpression<?> attribut) {
        this.attribut = attribut;
    }

    /**
     * Liefert das Standard-Sortierattribut zurück: {@link BenutzerSortierattribut#BENUTZERNAME}.
     *
     * @return das Standard-Sortierattribut
     */
    public static BenutzerSortierattribut getStandard() {
        return BENUTZERNAME;
    }

    @Override
    public ComparableExpression<?> getAttribut() {
        return attribut;
    }

}
