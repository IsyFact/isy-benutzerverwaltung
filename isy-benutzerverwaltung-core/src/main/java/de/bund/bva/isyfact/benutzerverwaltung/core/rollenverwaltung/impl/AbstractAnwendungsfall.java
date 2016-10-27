package de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.impl;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.FehlerSchluessel;

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

    protected final Validator validator;

    protected AbstractAnwendungsfall(Validator validator) {
        this.validator = validator;
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
