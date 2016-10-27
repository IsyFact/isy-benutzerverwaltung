package de.bund.bva.isyfact.benutzerverwaltung.sicherheit.exception;

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
