package de.bund.bva.isyfact.benutzerverwaltung.integration;

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

import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;


/**
 * Referenziert einen Benutzer der Benutzerverwaltung zur Integration in Anwendungen.
 * <br>
 * <br>
 * In die Entitäten der Anwendung kann sie wie folgt eingebunden werden:
 * <pre>
 * <code>
 * {@literal @}Embedded
 * {@literal @}AttributeOverride(name = "id", column = {@literal @}Column(name = "benutzer"))
 *  private BenutzerReferenz benutzer;
 * </code>
 * </pre>
 * Dadurch wird in der Tabelle eine Spalte mit dem Namen BENUTZER angelegt, welche die ID des Benutzers enthält.
 * <br>
 * Sollen mehr als eine BenutzerReferenz in einer Entität gespeichert werden, müssen die Spaltennamen
 * (<code>{@literal @}Column</code>) entsprechend angepasst werden.
 * <br>
 * Auf eine Liste von Benutzern kann so verwiesen werden:
 * <pre>
 * <code>
 * {@literal @}ElementCollection
 * {@literal @}CollectionTable(name = "ENTITAET_BENUTZER", joinColumns = {@literal @}JoinColumn(name = "ENTITAET_ID"))
 *  private List<BenutzerReferenz> benutzer = new ArrayList<>();
 * </code>
 * </pre>
 * Dadurch wird eine Tabelle mit zwei Spalten erzeugt. ID enthält die ID des Benutzers, ENTITAET_ID verweist auf die ID der beinhaltenden Entität.
 *
 */
@Embeddable
@Access(AccessType.FIELD)
@AttributeOverride(name = "id", column = @Column(name = "BENUTZER_ID"))
public class BenutzerReferenz {

    @NotNull
    private Long id;

    @Transient
    private BenutzerDaten daten;

    public BenutzerReferenz() {
    }

    /**
     * Erseugt eine BenutzerReferenz mit der übergebenen Id.
     * Das Feld daten wird nicht gesetzt.
     *
     * @param id Id der erzeugten BenutzerReferenz.
     */
    public BenutzerReferenz(Long id) {
        this.id = id;
    }

    /**
     * Erzeugt eine BenutzerReferenz und initialisiert diese mit den übergebenen Benutzerdaten.
     * Die Id wird auf den Wert der Id der Benutzerdaten gesetzt.
     *
     * @param daten
     */
    public BenutzerReferenz(BenutzerDaten daten) {
        id = daten.getId();
        this.daten = daten;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BenutzerDaten getDaten() {
        return daten;
    }

    public void setDaten(BenutzerDaten daten) {
        this.daten = daten;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BenutzerReferenz that = (BenutzerReferenz) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
