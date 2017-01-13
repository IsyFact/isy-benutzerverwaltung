package de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.daten;

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


import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.validation.UnbekannteRollenId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Schnittstellen-Objekt zum Anlegen einer Rolle.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class RolleAnlegen {

    private final String rollenId;

    private final String rollenName;

    /**
     * Erzeugt ein Schnittstellen-Objekt.
     *
     * @param rollenId   ID der Rolle
     * @param rollenName Name der Rolle
     */
    public RolleAnlegen(String rollenId, String rollenName) {
        this.rollenId = rollenId;
        this.rollenName = rollenName;
    }

    @UnbekannteRollenId
    @NotNull
    public String getRollenId() {
        return rollenId;
    }

    @NotNull
    @Size(min = 1, message = "{validation.rolle.name.leer}")
    public String getRollenName() {
        return rollenName;
    }

}
