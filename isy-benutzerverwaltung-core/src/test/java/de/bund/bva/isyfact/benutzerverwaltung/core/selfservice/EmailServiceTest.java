package de.bund.bva.isyfact.benutzerverwaltung.core.selfservice;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.core.selfservice.impl.EmailServiceImpl;
import de.bund.bva.pliscommon.konfiguration.common.Konfiguration;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {
    private EmailService emailService;

    String host = "mail.gmx.de";
    int port = 465;
    boolean sslEnable = true;
    boolean auth = true;
    String emailAdresse = "user@mail.com";
    String username = "self.service@gmx.de";
    String password = "self.service";
    String sender = "self.service@gmx.de";
    String token = "$2a$10$lj4tBJe5luAEI6";
    String subject = "Selfservice zum Zurücksetzen Ihres Passworts";
    String link = "http://localhost:8080/";
    String body = "Link: $link";

    @Mock
    private Konfiguration konfiguration;

    @Before
    public void initialize() {
        MessageSource messageSource = Mockito.mock(MessageSource.class);
        MessageSourceHolder messageSourceHolder = new MessageSourceHolder();
        messageSourceHolder.setMessageSource(messageSource);

        when(konfiguration.getAsString("selfservice.mail.smtp.host")).thenReturn(host);
        when(konfiguration.getAsInteger("selfservice.mail.smtp.port")).thenReturn(port);
        when(konfiguration.getAsBoolean("selfservice.mail.smtp.ssl.enable")).thenReturn(sslEnable);
        when(konfiguration.getAsBoolean("selfservice.mail.smtp.auth")).thenReturn(auth);
        when(konfiguration.getAsString("selfservice.mail.username")).thenReturn(username);
        when(konfiguration.getAsString("selfservice.mail.password")).thenReturn(password);
        when(konfiguration.getAsString("selfservice.mail.sender")).thenReturn(sender);

        emailService = new EmailServiceImpl(konfiguration);
    }

    @Test
    public void testErzeugeEmailMitToken()
            throws MessagingException, BenutzerverwaltungBusinessException, IOException {

        when(konfiguration.getAsString("selfservice.mail.body")).thenReturn(body);
        when(konfiguration.getAsString("selfservice.mail.link")).thenReturn(link);
        when(konfiguration.getAsString("selfservice.mail.subject")).thenReturn(subject);

        MimeMessage mimeMessage = (MimeMessage) emailService.erzeugeEmailMitToken(emailAdresse, token);
        assertEquals(emailAdresse, mimeMessage.getAllRecipients()[0].toString());
        assertEquals(subject, mimeMessage.getSubject());

        String expectedBody = body.replace("$link", link + token);
        assertEquals(expectedBody, mimeMessage.getContent().toString());
        String from = mimeMessage.getHeader("From")[0].toString();
        assertEquals(sender, from);
    }

}