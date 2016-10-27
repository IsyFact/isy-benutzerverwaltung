package de.bund.bva.isyfact.benutzerverwaltung.common.konstanten;

public class ValidierungSchluessel {

    /**
     * Ein Benutzer mit dem Namen {0} ist bereits vorhanden.
     */
    public static final String MSG_BENUTZERNAME_BEREITS_VORHANDEN =
        "validation.beanmanuell.benutzerbo.benutzername.bereitsVorhanden";

    /**
     * Ungültiger Benutzername: Es sind nur Alphanumerische Zeichen ('A-Z', 'a-z', '0-9'), Bindestriche ('-'),
     * Unterstriche ('_') und Punkte ('. ') erlaubt.
     */
    public static final String MSG_BENUTZERNAME_UNGUELTIG =
        "validation.beanmanuell.benutzerbo.benutzername.ungueltigeZeichen";

    /**
     * Benutzername nicht vorhanden.
     */
    public static final String MSG_BENUTZERNAME_NICHT_VORHANDEN =
        "validation.beanmanuell.benutzerbo.benutzername.nichtExistent";

    /**
     * Die Rolle mit dem Namen {0} ist nicht vorhanden.
     */
    public static final String MSG_ROLLE_NICHT_VORHANDEN =
        "validation.beanmanuell.benutzerbo.rolle.nichtExistent";

    /**
     * Bitte wählen Sie eine Rolle aus.
     */
    public static final String MSG_KEINE_ROLLE_AUSGEWAEHLT =
        "validation.beanmanuell.benutzerbo.rolle.nichtAusgewaehlt";

    /**
     * Das Password darf nicht leer sein.
     */
    public static final String MSG_PASSWORT_LEER = "validation.beanmanuell.benutzerbo.passwort.leer";

    public static final String MSG_PASSWORT_AENDERN_FALSCH = "validation.benutzer.passwort.aendern.falsch";

    public static final String MSG_PASSWORT_AENDERN_UNTERSCHIEDLICH =
        "validation.benutzer.passwort.unterschiedlich";

    public static final String MSG_ROLLE_BEREITS_VORHANDEN = "validation.rolle.vorhanden";

    public static final String ROLLE_ID_UNGUELTIG = "validation.rolle.id.ungueltig";

}
