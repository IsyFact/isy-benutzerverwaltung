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


import java.io.Serializable;

/**
 * Bündelt alle Suchkriterien für Rollen.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class RolleSuchkriterien implements Serializable {

    private static final long serialVersionUID = 0L;

    private String id;

    private String name;

    /**
     * Initialisierung. Standardmäßig wird nicht gefiltert (=null).
     */
    public RolleSuchkriterien() {
        id = null;
        name = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
