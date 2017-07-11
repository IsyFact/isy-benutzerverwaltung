package de.bund.bva.isyfact.benutzerverwaltung.core.selfservice;

/*-
 * #%L
 * IsyFact Benutzerverwaltung GUI mit Primefaces
 * %%
 * Copyright (C) 2016 - 2017 Bundesverwaltungsamt (BVA)
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {
    private EmailService emailService;

    private String host = "mail.gmx.de";
    private int port = 465;
    private boolean sslEnable = true;
    private boolean auth = true;
    private String emailAdresse = "user@mail.com";
    private String username = "self.service@gmx.de";
    private String password = "self.service";
    private String sender = "self.service@gmx.de";
    private String token = "$2a$10$lj4tBJe5luAEI6";
    private String subject = "Selfservice zum Zurücksetzen Ihres Passworts";
    private String link = "http://localhost:8080/";
    private String body = "Link: $link";

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
        String from = mimeMessage.getHeader("From")[0];
        assertEquals(sender, from);
    }

}