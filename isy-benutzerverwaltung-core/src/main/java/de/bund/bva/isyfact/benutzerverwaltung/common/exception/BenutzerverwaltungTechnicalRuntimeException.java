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

import de.bund.bva.pliscommon.exception.PlisTechnicalRuntimeException;
import de.bund.bva.pliscommon.util.exception.MessageSourceFehlertextProvider;

/**
 * Die Basisklasse für alle technischen RuntimeExceptions der Komponente Benutzerverwaltung.
 * (<i>unchecked</i>).
 * 
 * @author Capgemini, Jonas Zitz
 * @version $Id: BenutzerverwaltungTechnicalRuntimeException.java 41593 2013-07-11 12:41:20Z jozitz $
 */
public class BenutzerverwaltungTechnicalRuntimeException extends PlisTechnicalRuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Konstruktor mit AusnahmeId und Parameter für einen Fehlertext, welche an die Vaterklasse weitergegeben
     * werden.
     * 
     * @param ausnahmeId
     *            Die AusnahmeId
     * @param parameter
     *            Die Parameter
     */
    public BenutzerverwaltungTechnicalRuntimeException(String ausnahmeId, String... parameter) {
        this(ausnahmeId, null, parameter);
    }

    /**
     * Konstruktor mit AusnahmeId, auslösendem Fehler und Parametern für die Fehlernachricht.
     * 
     * @param ausnahmeId
     *            Die Id der Ausnahme
     * @param throwable
     *            Der auslösende Fehler
     * @param parameter
     *            Parameter für den Fehlertext
     */
    public BenutzerverwaltungTechnicalRuntimeException(String ausnahmeId, Throwable throwable,
        String... parameter) {
        super(ausnahmeId, throwable, new MessageSourceFehlertextProvider(), parameter);
    }

}
