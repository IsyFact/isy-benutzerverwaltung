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


import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Benutzer;

/**
 * Dieser Datentyp definiert den Status eines Benutzers innerhalb der Benutzerverwaltung. Das Attribut
 * {@link BenutzerStatus#getBezeichnung() Bezeichnung} dient fuer eine sprechende Bezeichnung.
 * 
 * @author Capgemini, Jonas Zitz
 * @version $Id: BenutzerStatus.java 41870 2013-07-25 13:54:34Z jozitz $
 */
public enum BenutzerStatus {

    /**
     * Der
     * {@link Benutzer
     * Benutzer} ist deaktiviert.
     */
    DEAKTIVIERT("Deaktiviert"),

    /**
     * Der
     * {@link Benutzer
     * Benutzer} ist gesperrt.
     */
    GESPERRT("Gesperrt"),

    /**
     * Der
     * {@link Benutzer
     * Benutzer} ist aktiviert.
     */
    AKTIVIERT("Aktiviert");

    private String bezeichnung;

    /**
     * Konstruktor.
     */
    private BenutzerStatus(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    /**
     * Diese Methode liefert zu einem gegebenen Index den {@link BenutzerStatus} auf Basis von
     * {@link BenutzerStatus#values()}.
     *
     * @param index
     *            ist der Index
     * @return ist der {@link BenutzerStatus}, passend zu diesem Index.
     */
    public static BenutzerStatus getBenutzerStatus(int index) {
        BenutzerStatus[] benutzerZustaende = BenutzerStatus.values();

        if (index > benutzerZustaende.length || index < 0) {
            throw new IllegalArgumentException("Der uebergebene BenutzerStatus-Wert \"" + index
                + "\" kann in keinen BenutzerStatus umgewandelt werden.");
        }
        return benutzerZustaende[index];
    }

    /**
     * This method gets the field <tt>bezeichnung</tt>.
     *
     * @return the field bezeichnung
     */
    public String getBezeichnung() {
        return bezeichnung;
    }

    @Override
    public String toString() {
        return bezeichnung;
    }

}
