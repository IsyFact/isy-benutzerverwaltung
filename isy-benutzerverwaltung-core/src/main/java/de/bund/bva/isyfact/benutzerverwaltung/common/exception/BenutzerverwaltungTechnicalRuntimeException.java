package de.bund.bva.isyfact.benutzerverwaltung.common.exception;

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
