package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.passwortpolicy;

/**
 * Enthält das Ergebnis der Prüfung einer Passwort Policy.
 */
public class ValidationResult {

    private boolean isValid;

    private String message;

    public ValidationResult(boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }

    /**
     * Das Ergebnis der Prüfung.
     *
     * @return true, wenn das Passwort der Policy entspricht, sonst false
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * Eine Nachricht zum Ergebnis der Prüfung.
     *
     * @return die Nachricht
     */
    public String getMessage() {
        return message;
    }
}
