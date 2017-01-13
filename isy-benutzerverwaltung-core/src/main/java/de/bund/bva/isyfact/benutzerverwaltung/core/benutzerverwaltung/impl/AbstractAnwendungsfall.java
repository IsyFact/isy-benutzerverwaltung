package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.impl;

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

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungTechnicalRuntimeException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.FehlerSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.ValidierungSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.BenutzerDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Benutzer;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementiert querschnittliche Funktionalität, die in allen konkreten Anwendungsfällen benötigt wird.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
abstract class AbstractAnwendungsfall {

    protected final BenutzerDao benutzerDao;

    protected final Validator validator;

    protected AbstractAnwendungsfall(BenutzerDao benutzerDao, Validator validator) {
        this.benutzerDao = benutzerDao;
        this.validator = validator;
    }

    protected Benutzer leseBenutzer(String benutzername) throws BenutzerverwaltungBusinessException {
        if (benutzername == null) {
            throw new BenutzerverwaltungTechnicalRuntimeException(FehlerSchluessel.MSG_EINGABEDATEN_FEHLEN);
        }

        Benutzer benutzer = benutzerDao.sucheMitBenutzername(benutzername);
        if (benutzer == null) {
            throw new BenutzerverwaltungBusinessException(
                ValidierungSchluessel.MSG_BENUTZERNAME_NICHT_VORHANDEN, benutzername);
        }

        return benutzer;
    }

    protected void validiere(Object... objects) throws BenutzerverwaltungValidationException {
        List<ConstraintViolation<?>> violations =
            Arrays.stream(objects).filter(Objects::nonNull).map(object -> validator.validate(object))
                .flatMap(Collection::stream).collect(Collectors.toList());
        if (!violations.isEmpty()) {
            throw new BenutzerverwaltungValidationException(FehlerSchluessel.MSG_EINGABEDATEN_UNGUELTIG,
                violations);
        }
    }
}
