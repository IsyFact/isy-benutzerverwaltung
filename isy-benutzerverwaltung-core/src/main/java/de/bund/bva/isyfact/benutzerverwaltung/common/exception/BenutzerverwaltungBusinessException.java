package de.bund.bva.isyfact.benutzerverwaltung.common.exception;

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

import de.bund.bva.pliscommon.exception.PlisBusinessException;
import de.bund.bva.pliscommon.util.exception.MessageSourceFehlertextProvider;

/**
 * Beschreibt einen fachlichen Fehler bei der Benutzung des Anwendungskerns.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
public class BenutzerverwaltungBusinessException extends PlisBusinessException {

    private static final long serialVersionUID = 1L;

    public BenutzerverwaltungBusinessException(String ausnahmeId, String... parameter) {
        super(ausnahmeId, new MessageSourceFehlertextProvider(), parameter);
    }

}
