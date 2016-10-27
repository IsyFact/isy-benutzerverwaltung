package de.bund.bva.isyfact.benutzerverwaltung.common.datentyp;

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
