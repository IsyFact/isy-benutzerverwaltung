package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation;

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

import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.PasswortZuruecksetzen;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PasswoerterStimmenUebereinValidator
    implements ConstraintValidator<PasswoerterStimmenUeberein, PasswortZuruecksetzen> {

    @Override
    public void initialize(PasswoerterStimmenUeberein constraintAnnotation) {
    }

    @Override
    public boolean isValid(PasswortZuruecksetzen value, ConstraintValidatorContext context) {
        return value == null || Objects
            .equals(value.getNeuesPasswort(), value.getNeuesPasswortBestaetigung());
    }
}
