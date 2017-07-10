package de.bund.bva.isyfact.benutzerverwaltung.core.selfservice;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;

import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * Hilfsklasse zum Versenden der Emails.
 *
 * @author Bjoern Saxe, msg systems ag
 * @author Alexander Salvanos, msg systems ag
 *
 */
public interface EmailService {

    /**
     * Erzeugt eine Email, über den der Benutzer einen Token-Link erhält.
     *
     * @param emailAdresse Die Emailadresse des Empfängers
     * @param token Das Token für den Link.
     * @throws MessagingException
     */
    Message erzeugeEmailMitToken(String emailAdresse, String token) throws BenutzerverwaltungBusinessException;

    /**
     * Versendet eine Email an den Benutzer.
     *
     * @param message Die Emailnachricht für den Benutzer.
     * @return  true, wenn die Email verschickt werden konnte.
     *          false, wenn die Email nicht verschickt werden konnte.
     * @throws BenutzerverwaltungBusinessException
     */
    boolean sendeEmail(Message message) throws BenutzerverwaltungBusinessException;
}
