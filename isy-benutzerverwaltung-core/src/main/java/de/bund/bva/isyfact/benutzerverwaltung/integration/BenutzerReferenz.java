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



/**
 * Referenziert einen Benutzer der Benutzerverwaltung zur Integration in Anwendungen.
 */
@Embeddable
@Access(AccessType.FIELD)
@AttributeOverride(name = "id", column = @Column(name = "benutzer_id"))
public class BenutzerReferenz {

    @NotNull
    private Long id;

    @Transient
    private BenutzerDaten daten;

    public BenutzerReferenz() {
    }

    public BenutzerReferenz(Long id) {
        this.id = id;
    }

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
}
