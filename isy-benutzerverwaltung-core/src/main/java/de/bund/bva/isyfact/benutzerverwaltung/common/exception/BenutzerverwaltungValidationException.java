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

import javax.validation.ConstraintViolation;
import java.util.Collections;
import java.util.List;

/**
 * Beschreibt einen Fehler bei der Validation von Daten. Die konkreten Fehler werden als eine Liste von
 * {@link ConstraintViolation Fehlern} mitgegeben.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
public class BenutzerverwaltungValidationException extends BenutzerverwaltungBusinessException {

    private static final long serialVersionUID = 1L;

    private List<ConstraintViolation<?>> fehler;

    public BenutzerverwaltungValidationException(String ausnahmeId, List<ConstraintViolation<?>> violations) {
        super(ausnahmeId);
        fehler = Collections.unmodifiableList(violations);
    }

    public List<ConstraintViolation<?>> getFehler() {
        return fehler;
    }

}
