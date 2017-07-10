package de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao;

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


import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.BenutzerToken;
import de.bund.bva.pliscommon.persistence.dao.Dao;

import java.util.Date;

/**
 * Datenzugriff für das BenutzerToken.
 *
 * @author Bjoern Saxe, msg systems ag
 * @author Alexander Salvanos, msg systems ag
 */
public interface BenutzerTokenDao extends Dao<BenutzerToken, String> {

    /**
     * Löscht alle Tokens eines Benutzers.
     *
     * @param benutzerName Der Benutzername des Benutzers.
     */
    void loescheTokensFuerBenutzer(String benutzerName);

    /**
     * Löscht Tokens, deren Ablaufdatum vor einem bestimmten Datum liegen.
     *
     * @param datum
     */
    void loescheTokensVorDatum(Date datum);

}
