package de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung;

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
