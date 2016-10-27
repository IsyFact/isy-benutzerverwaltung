package de.bund.bva.isyfact.benutzerverwaltung.common.exception;

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
