package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung;

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
