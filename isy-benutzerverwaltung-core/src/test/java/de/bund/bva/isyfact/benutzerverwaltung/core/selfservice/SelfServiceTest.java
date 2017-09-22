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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Suchergebnis;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.KonfigurationsSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSuchkriterien;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.Benutzerverwaltung;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.PasswortZuruecksetzen;
import de.bund.bva.isyfact.benutzerverwaltung.core.selfservice.impl.SelfServiceImpl;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.BenutzerDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Benutzer;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.selfservice.dao.BenutzerTokenDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.selfservice.entity.BenutzerToken;
import de.bund.bva.pliscommon.konfiguration.common.Konfiguration;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SelfServiceTest {

    private SelfService selfService;

    @Mock
    private Benutzerverwaltung benutzerverwaltung;

    @Mock
    private BenutzerTokenDao benutzerTokenDao;

    @Mock
    private BenutzerDao benutzerDao;

    @Mock
    private EmailService emailService;

    @Mock
    private Konfiguration konfiguration;

    @Before
    public void initialize() {
        MessageSource messageSource = Mockito.mock(MessageSource.class);
        MessageSourceHolder messageSourceHolder = new MessageSourceHolder();
        messageSourceHolder.setMessageSource(messageSource);

        selfService = new SelfServiceImpl(
                benutzerverwaltung,
                benutzerTokenDao,
                benutzerDao,
                emailService,
                konfiguration);
    }

    @Test
    public void testSendePasswortZuruecksetzenEmailMitEmailNichtVorhanden() throws Exception {
        String emailadresse = "gibtes@nicht";

        BenutzerSuchkriterien suchkriterien = new BenutzerSuchkriterien();
        suchkriterien.setEmail(emailadresse);
        Suchergebnis<BenutzerDaten> keineTreffer = new Suchergebnis<>(null, 0);
        when(benutzerverwaltung.sucheBenutzer(suchkriterien, null,null)).thenReturn(keineTreffer);

        assertFalse(selfService.sendePasswortZuruecksetzenEmail(emailadresse));

        verify(benutzerverwaltung).sucheBenutzer(suchkriterien, null, null);
    }

    @Test
    public void testSendePasswortZuruecksetzenEmailMitEmailVorhanden() throws Exception {
        String emailAdresse = "gibtes@doch";
        String benutzerName = "Michael Mustermann";

        BenutzerSuchkriterien suchkriterien = new BenutzerSuchkriterien();
        suchkriterien.setEmail(emailAdresse);

        BenutzerDaten benutzerDaten = new BenutzerDaten();
        benutzerDaten.setBenutzername(benutzerName);
        benutzerDaten.setEmailAdresse(emailAdresse);
        benutzerDaten.setId(123L);

        List<BenutzerDaten> trefferliste = new ArrayList<>();
        trefferliste.add(benutzerDaten);

        Suchergebnis<BenutzerDaten> treffer = new Suchergebnis<>(trefferliste, 1);
        when(benutzerverwaltung.sucheBenutzer(suchkriterien, null,null)).thenReturn(treffer);

        Benutzer benutzer =  new Benutzer();
        benutzer.setId(123L);

        when(benutzerDao.sucheMitId(123L)).thenReturn(benutzer);

        when(konfiguration.getAsInteger(KonfigurationsSchluessel.CONF_SELFSERVICE_TOKEN_ABGELAUFEN_IN_STUNDEN, 48)).thenReturn(48);

        when(emailService.erzeugeEmailMitToken(anyString(), anyString())).thenReturn(new MimeMessage((Session)null));

        assertTrue(selfService.sendePasswortZuruecksetzenEmail(emailAdresse));

        verify(benutzerverwaltung).sucheBenutzer(suchkriterien, null, null);

        verify(benutzerTokenDao).loescheTokensFuerBenutzer(benutzerName);

        // Überprüfung mit any(Date.class), um festen Zeitpunkt (von new Date()) zu umgehen.
        verify(benutzerTokenDao).loescheTokensVorDatum(any(Date.class));

        ArgumentCaptor<BenutzerToken> tokenArgumentCaptor = ArgumentCaptor.forClass(BenutzerToken.class);
        verify(benutzerTokenDao).speichere(tokenArgumentCaptor.capture());

        BenutzerToken gespeichertesBenutzerToken = tokenArgumentCaptor.getValue();

        assertEquals(123L, (long)gespeichertesBenutzerToken.getBenutzer().getId());
        assertNotNull(gespeichertesBenutzerToken.getToken());
        assertNotNull(gespeichertesBenutzerToken.getAblaufDatum());

        verify(emailService).erzeugeEmailMitToken(emailAdresse, gespeichertesBenutzerToken.getToken());
        verify(emailService).sendeEmail(any(MimeMessage.class));
    }

    @Test(expected = BenutzerverwaltungBusinessException.class)
    public void testHoleBenutzernameZuTokenKeinTokenVorhanden() throws BenutzerverwaltungBusinessException {
        final String token = "nicht vorhanden";

        when(benutzerTokenDao.sucheMitId(token)).thenReturn(null);

        String benutzerName = selfService.holeBenutzernameZuToken(token);

        Mockito.verify(selfService).holeBenutzernameZuToken(token);
    }

    @Test(expected = BenutzerverwaltungBusinessException.class)
    public void testHoleBenutzernameZuTokenBeiAblaufdatumUngueltig() throws BenutzerverwaltungBusinessException {
        final BenutzerToken benutzerToken = new BenutzerToken();
        benutzerToken.setAblaufDatum(new Date(0));

        final String token = "Token mit ungueltigem Datum";

        when(benutzerTokenDao.sucheMitId(token)).thenReturn(benutzerToken);

        selfService.holeBenutzernameZuToken(token);
    }

    @Test
    public void testHoleBenutzernameZuToken() throws BenutzerverwaltungBusinessException {
        final String benutzername = "Mustermann";

        final Benutzer benutzer = new Benutzer();
        benutzer.setBenutzername(benutzername);
        final BenutzerToken benutzerToken = new BenutzerToken();
        benutzerToken.setBenutzer(benutzer);
        benutzerToken.setAblaufDatum(new Date(Long.MAX_VALUE));

        String token = UUID.randomUUID().toString();

        when(benutzerTokenDao.sucheMitId(token)).thenReturn(benutzerToken);

        String benutzernameZuToken = selfService.holeBenutzernameZuToken(token);
        assertEquals("Der Benutzername sollte " + benutzername + " sein!", benutzername, benutzernameZuToken);
    }

    @Test
    public void testSetzePasswortZurueck() throws Exception {
        PasswortZuruecksetzen passwortZuruecksetzen = new PasswortZuruecksetzen("benutzername", "passwort", "passwort");

        BenutzerDaten benutzerDaten = new BenutzerDaten();
        benutzerDaten.setBenutzername("benutzername");
        benutzerDaten.setPasswort("passwort");

        when(benutzerverwaltung.setzePasswortZurueck(passwortZuruecksetzen)).thenReturn(benutzerDaten);

        BenutzerDaten geanderterBenutzer = selfService.setzePasswortZurueck(passwortZuruecksetzen);

        assertEquals("benutzername", geanderterBenutzer.getBenutzername());
        assertEquals("passwort", geanderterBenutzer.getPasswort());

        verify(benutzerverwaltung).setzePasswortZurueck(passwortZuruecksetzen);
        verify(benutzerTokenDao).loescheTokensFuerBenutzer("benutzername");
    }
}
