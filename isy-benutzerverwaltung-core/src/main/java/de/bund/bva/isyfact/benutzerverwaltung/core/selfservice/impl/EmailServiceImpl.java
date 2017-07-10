package de.bund.bva.isyfact.benutzerverwaltung.core.selfservice.impl;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.FehlerSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.core.selfservice.EmailService;
import de.bund.bva.pliscommon.konfiguration.common.Konfiguration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Hilfsklasse zum Versenden der Emails.
 *
 * @author Bjoern Saxe, msg systems ag
 * @author Alexander Salvanos, msg systems ag
 *
 */
public class EmailServiceImpl implements EmailService {
    private final Konfiguration konfiguration;
    private Session session;

    public EmailServiceImpl(Konfiguration konfiguration) {
        this.konfiguration = konfiguration;
    }

    @Override
    public MimeMessage erzeugeEmailMitToken(String emailAdresse, String token) throws BenutzerverwaltungBusinessException {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host",
                konfiguration.getAsString("selfservice.mail.smtp.host"));
        properties.setProperty("mail.smtp.port",
                Integer.toString(konfiguration.getAsInteger("selfservice.mail.smtp.port")));
        properties.setProperty("mail.smtp.ssl.enable",
                Boolean.toString(konfiguration.getAsBoolean("selfservice.mail.smtp.ssl.enable")));

        if (konfiguration.getAsBoolean("selfservice.mail.smtp.auth")) {
            properties.setProperty("mail.smtp.auth", "true");
            Authenticator authenticator = new Authenticator() {
                private PasswordAuthentication pa = new PasswordAuthentication(
                        konfiguration.getAsString("selfservice.mail.username"),
                        konfiguration.getAsString("selfservice.mail.password"));

                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return pa;
                }
            };
            session = Session.getInstance(properties, authenticator);
        } else {
            session = Session.getInstance(properties);
        }

        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(konfiguration.getAsString("selfservice.mail.sender")));

            InternetAddress[] address = {new InternetAddress(emailAdresse)};
            mimeMessage.setRecipients(Message.RecipientType.TO, address);
            mimeMessage.setSubject(konfiguration.getAsString("selfservice.mail.subject"));
            mimeMessage.setSentDate(new Date());

            String body = konfiguration.getAsString("selfservice.mail.body");
            String link = konfiguration.getAsString("selfservice.mail.link") + token;

            body = body.replace("$link", link);
            mimeMessage.setText(body);
        } catch (MessagingException e) {
            throw new BenutzerverwaltungBusinessException(FehlerSchluessel.MSG_EMAIL_VERSENDEN_IST_FEHLGESCHLAGEN);
        }
        return mimeMessage;
    }

    @Override
    public boolean sendeEmail(Message message) throws BenutzerverwaltungBusinessException {
        boolean successful;
        try {
           Transport.send(message);
           successful = true;
        } catch (MessagingException e) {
            throw new BenutzerverwaltungBusinessException(FehlerSchluessel.MSG_EMAIL_VERSENDEN_IST_FEHLGESCHLAGEN);
        }
        return successful;
    }
}
