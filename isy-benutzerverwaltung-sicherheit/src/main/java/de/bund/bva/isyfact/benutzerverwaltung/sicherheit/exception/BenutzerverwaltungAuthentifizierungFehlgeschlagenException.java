package de.bund.bva.isyfact.benutzerverwaltung.sicherheit.exception;

/*-
 * #%L
 * IsyFact Benutzerverwaltung Sicherheit
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

import de.bund.bva.pliscommon.exception.PlisBusinessException;
import de.bund.bva.pliscommon.util.exception.MessageSourceFehlertextProvider;

/**
 * Beschreibt einen fehlerhaften Anmeldeversuch eines Benutzers.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class BenutzerverwaltungAuthentifizierungFehlgeschlagenException extends PlisBusinessException {

    private static final long serialVersionUID = 0L;

    /**
     * Konstruktor mit AusnahmeId und Parameter für einen Fehlertext, welche an die Vaterklasse weitergegeben
     * werden.
     *
     * @param ausnahmeId Die AusnahmeId
     * @param parameter  Die Parameter
     */
    public BenutzerverwaltungAuthentifizierungFehlgeschlagenException(String ausnahmeId,
        String... parameter) {
        super(ausnahmeId, new MessageSourceFehlertextProvider(), parameter);
    }

}
