package de.bund.bva.isyfact.benutzerverwaltung.sicherheit;

import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.bund.bva.pliscommon.aufrufkontext.impl.AufrufKontextVerwalterImpl;

/**
 * Implementiert einen Aufrufkontext-Verwalter, der den Aufrufkontext in der Session speichert. Der
 * Aufrufkontext-Verwalter nutzt im Hintergrund einen {@link LoginVerwalter}, der im Session Scope liegen
 * muss.<br/>
 * <b>Wichtig:</b> Die Implementierung funktioniert nur für Single-Server-Anwendungen bzw. für
 * Anwendungen in einem Cluster, bei denen ein Load-Balancer einen Benutzer immer auf den gleichen Server
 * schickt (Sticky Sessions).
 *
 * @author msg systems ag, Dirk Jäger
 */
public class SessionAufrufKontextVerwalter<T extends BenutzerverwaltungAufrufKontextImpl>
    extends AufrufKontextVerwalterImpl<T> {

    private static final IsyLogger LOG = IsyLoggerFactory.getLogger(SessionAufrufKontextVerwalter.class);

    /**
     * Der LoginVerwalter, der den Aufrufkontext in der Session zwischenspeichert.
     * Der AufrufKontextVerwalter selbst ist Thread-Scoped, es kann darum nicht
     * garantiert werden, dass bei einem Request ein AufrufKontext gespeichert ist.
     */
    private LoginVerwalter<T> loginVerwalter;

    @Override
    public T getAufrufKontext() {
        // Überprüfe, ob einen Aufrufkontext gespeichert ist.
        if (super.getAufrufKontext() == null) {
            // Wenn keine Aufrufkontext gespeichert ist, lese ihn aus dem LoginVerwalter.
            LOG.debug("Lese Aufrufkontext aus LoginVerwalter");
            T aufrufKontext = loginVerwalter.getAufrufKontext();

            // Ein paar Debug-Ausgaben
            if (aufrufKontext != null) {
                LOG.debugFachdaten("Aufrufkontext erfolgreich ermittelt für Benutzer {}.",
                    aufrufKontext.getDurchfuehrenderBenutzerKennung());
            } else {
                aufrufKontext = (T) new BenutzerverwaltungAufrufKontextImpl();
                LOG.debug("Kein Aufrufkontext vorhanden.");
            }
            super.setAufrufKontext(aufrufKontext);
        }
        return super.getAufrufKontext();
    }

    @Override
    public void setAufrufKontext(T aufrufkontext) {
        // Aufrufkontext in der Session abspeichern, damit der Benutzer zwischen Aufrufen angemeldet bleibt.
        loginVerwalter.setAufrufKontext(aufrufkontext);
        // Aufrufkontext für den aktuellen Request setzen.
        super.setAufrufKontext(aufrufkontext);
    }

    public LoginVerwalter<T> getLoginVerwalter() {
        return loginVerwalter;
    }

    public void setLoginVerwalter(LoginVerwalter<T> loginVerwalter) {
        this.loginVerwalter = loginVerwalter;
    }

}
