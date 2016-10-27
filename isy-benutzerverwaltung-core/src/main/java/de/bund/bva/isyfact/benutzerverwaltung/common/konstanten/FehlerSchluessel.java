package de.bund.bva.isyfact.benutzerverwaltung.common.konstanten;

/**
 * Die Fehler Schluessel, die diese Konstante fuer Exceptions auf die Fehler.properties abbildet.
 *
 * @author Capgemini, Jonas Zitz
 */
public class FehlerSchluessel {

    /**
     * Es ist ein technischer Fehler aufgetreten {0}. Bitte wenden Sie sich an den/die Administrator/in des
     * Systems. Referenzcode: {1}
     */
    public static final String ALLGEMEINER_TECHNISCHER_FEHLER = "BENVZ99999";

    /**
     * Authentifzierung fehlgeschlagen.
     */
    public static final String MSG_AUTHENTIFIZIERUNG_FEHLGESCHLAGEN = "BENVZ00001";

    /**
     * Benutzer Account gesperrt. Bitte melden Sie sich bei einem Administrator.
     */
    public static final String MSG_BENUTZER_GESPERRT = "BENVZ00002";

    /**
     * Der Benutzer kann nicht gelöscht werden, da in der Anwendung noch auf ihn verwiesen wird.
     */
    public static final String BENUTZER_LOESCHEN_NICHT_MOEGLICH = "BENVW10100";

    /**
     * Der Benutzer kann nicht gelöscht werden, da in der Anwendung noch auf ihn verwiesen wird.
     */
    public static final String ROLLE_LOESCHEN_NICHT_MOEGLICH = "BENVW10101";

    /**
     * Technischer Fehler in der Datenbank.
     */
    public static final String MSG_TECHNISCHER_FEHLER_DATENBANK = "BENVW10900";

    /**
     * Technischer Fehler beim Konvertieren.
     */
    public static final String MSG_TECHNISCHER_FEHLER_KONVERTIERUNG = "BENVW10901";

    /**
     * Validieren der Eingabedaten fehlgeschlagen.
     */
    public static final String MSG_EINGABEDATEN_UNGUELTIG = "BENVW10910";

    /**
     * Eingabedaten fehlen.
     */
    public static final String MSG_EINGABEDATEN_FEHLEN = "BENVW10911";

    /**
     * Technischer Fehler bei der Abmeldung eines Benutzers.
     */
    public static final String MSG_TECHNISCHER_FEHLER_ABMELDUNG = "BENVW10920";

}
