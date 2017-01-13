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


import java.util.Arrays;
import java.util.List;

/**
 * Stellt Testdaten für Tests mit Rollen zur Verfügung.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class RollenTestdaten {

    public static final String ROLLE_MITGLIED_ID = "MITGLIED";

    public static final String ROLLE_MITGLIED_NAME = "Standard-Rolle für neue Benutzer";

    public static final String ROLLE_ADMINISTRATOR_ID = "ADMINISTRATOR";

    public static final String ROLLE_ADMINISTRATOR_NAME = "Administrator-Rolle mit vollen Rechten";

    public static final String ROLLE_MODERATOR_ID = "MODERATOR";

    public static final String ROLLE_MODERATOR_NAME = "Administrator-Rolle mit Rechten zur Moderation";

    public static final List<String> ROLLEN_IDS =
        Arrays.asList(ROLLE_MITGLIED_ID, ROLLE_ADMINISTRATOR_ID, ROLLE_MODERATOR_ID);

    public static final List<String> ROLLEN_NAMEN =
        Arrays.asList(ROLLE_MITGLIED_NAME, ROLLE_ADMINISTRATOR_NAME, ROLLE_MODERATOR_NAME);

    private RollenTestdaten() {
    }

}
