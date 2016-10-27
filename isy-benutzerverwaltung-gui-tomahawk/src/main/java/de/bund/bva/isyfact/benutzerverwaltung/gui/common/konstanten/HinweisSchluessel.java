package de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten;

/**
 * Konstanten für den Zugriff auf die Schlüssel von Hinweisen.
 *
 * @author msg systems, Stefan Dellmuth
 */
public abstract class HinweisSchluessel {

    /**
     * Der Benutzer mit Benutzernamen '{0}' wurde erfolgreich gelöscht.
     */
    public static final String BENUTZER_GELOESCHT = "BENVW80001";

    /**
     * Der Benutzer mit Benutzernamen '{0}' wurde erstellt.
     */
    public static final String BENUTZER_ERSTELLT = "BENVW80003";

    /**
     * Der Benutzer mit Benutzernamen '{0}' wurde aktualisiert.
     */
    public static final String BENUTZER_AKTUALISIERT = "BENVW80004";

    /**
     * Die gewählten Benutzer wurden erfolgreich aktualisiert.
     */
    public static final String ROLLENZUWEISUNG_BENUTZER_AKTUALISIERT = "BENVW80005";

    /**
     * Das Passwort des gewählten Benutzers wurde erfolgreich zurückgesetzt.
     */
    public static final String BENUTZER_PASSWORT_ZURUECKGESETZT = "BENVW80006";

    /**
     * Der Benutzer hat sein Passwort selbst geändert.
     */
    public static final String BENUTZER_SELBST_PASSWORT_GEAENDERT = "BENVW80007";

    /**
     * Die Rolle '{0}' wurde erfolgreich erstellt.
     */
    public static final String ROLLE_ERSTELLT = "BENVW80011";

    /**
     * Die Rolle '{0}' wurde erfolgreich geändert.
     */
    public static final String ROLLE_GEAENDERT = "BENVW80012";

    private HinweisSchluessel() {
    }

}
