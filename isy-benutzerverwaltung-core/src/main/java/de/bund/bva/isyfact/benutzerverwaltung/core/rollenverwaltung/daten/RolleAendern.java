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


import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.validation.BekannteRollenId;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.validation.UnbekannteRollenId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Schnittstellen-Objekt zum Ändern einer Rolle.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class RolleAendern {

    private final String alteRollenId;

    private final String neueRollenId;

    private final String rollenName;

    /**
     * Erzeugt ein Schnittstellen-Objekt.
     *
     * @param alteRollenId alte ID der Rolle
     * @param neueRollenId neue ID der Rolle
     * @param rollenName   neuer Name der Rolle
     */
    public RolleAendern(String alteRollenId, String neueRollenId, String rollenName) {
        this.alteRollenId = alteRollenId;
        this.neueRollenId = neueRollenId;
        this.rollenName = rollenName;
    }

    @BekannteRollenId
    @NotNull
    public String getAlteRollenId() {
        return alteRollenId;
    }

    @UnbekannteRollenId
    public String getNeueRollenId() {
        return neueRollenId;
    }

    @NotNull
    @Size(min = 1, message = "{validation.rolle.name.leer}")
    public String getRollenName() {
        return rollenName;
    }

}
