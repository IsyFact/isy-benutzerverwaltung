package de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity;

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

import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.NamedQuerySchluessel;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Die persistente Entität einer {@link Rolle}.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
@Entity
@NamedQueries({ @NamedQuery(name = NamedQuerySchluessel.SUCHE_ROLLE_ALLE, query = "Select r from Rolle r"),
                  @NamedQuery(name = NamedQuerySchluessel.SUCHE_ROLLE_ID,
                              query = "Select r from Rolle r where r.id = :id"),
                  @NamedQuery(name = NamedQuerySchluessel.SUCHE_ROLLE_IDS,
                              query = "Select r from Rolle r where r.id in :ids") })
public class Rolle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long primaryKey;

    private String id;

    private String name;

    private int version;

    /**
     * Gibt den Primärschlüssel zurück.
     *
     * @return den Primärschlüssel.
     */
    @Id
    @GeneratedValue
    public Long getPrimaryKey() {
        return primaryKey;
    }

    /**
     * Setzt den Primärschlüssel.
     *
     * @param primaryKey neuer Primärschlüssel
     */
    public void setPrimaryKey(Long primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * Gibt die ID der Rolle gemäß isy-sicherheit zurück.
     *
     * @return die ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Setzt die ID der Rolle gemäß isy-sicherheit.
     *
     * @param id neue ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gibt den Namen der Rolle gemäß isy-sicherheit zurück.
     *
     * @return den Namen.
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt die ID der Rolle gemäß isy-sicherheit.
     *
     * @param name neuer Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the field 'version'.
     *
     * @return Value of version
     */
    @Version
    public int getVersion() {
        return version;
    }

    /**
     * Sets the field 'version'.
     *
     * @param version New value for version
     */
    public void setVersion(int version) {
        this.version = version;
    }

}
