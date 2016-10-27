package de.bund.bva.isyfact.benutzerverwaltung.common.exception;

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
