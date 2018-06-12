package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.passwortpolicy;

/**
 * Interface für zur Implementierung einer Passwort Policy.
 */
@FunctionalInterface
public interface PasswortPolicy {
    /**
     * Überprüft die Passwort Policy für das Passwort und den Benutzer.
     *
     * @param passwort das Passwort
     * @param benutzername der Benutzer, für den das Passwort gelten soll
     * @return {@link ValidationResult} mit dem Ergebnis der Prüfung
     */
    ValidationResult validate(String passwort, String benutzername);
}
