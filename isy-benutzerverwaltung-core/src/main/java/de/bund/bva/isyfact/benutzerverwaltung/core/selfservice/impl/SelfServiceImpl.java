package de.bund.bva.isyfact.benutzerverwaltung.core.selfservice.impl;

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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import javax.mail.Message;

import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Suchergebnis;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.FehlerSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.KonfigurationsSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSuchkriterien;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.Benutzerverwaltung;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.PasswortZuruecksetzen;
import de.bund.bva.isyfact.benutzerverwaltung.core.selfservice.EmailService;
import de.bund.bva.isyfact.benutzerverwaltung.core.selfservice.SelfService;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.BenutzerDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.BenutzerTokenDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Benutzer;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.BenutzerToken;
import de.bund.bva.pliscommon.konfiguration.common.Konfiguration;

/**
 * Implementierungsklasse vom SelfService.
 *
 * @author Bjoern Saxe, msg systems ag
 * @author Alexander Salvanos, msg systems ag
 *
 */
public class SelfServiceImpl implements SelfService {
    private final static int SELFSERVICE_TOKEN_DEFAULT_DAUER_STUNDEN = 48;

    private final Benutzerverwaltung benutzerverwaltung;
    private final BenutzerTokenDao benutzerTokenDao;
    private final BenutzerDao benutzerDao;
    private final EmailService emailService;
    private final Konfiguration konfiguration;

    public SelfServiceImpl(
            Benutzerverwaltung benutzerverwaltung,
            BenutzerTokenDao benutzerTokenDao,
            BenutzerDao benutzerDao,
            EmailService emailService,
            Konfiguration konfiguration) {
        this.benutzerverwaltung = benutzerverwaltung;
        this.benutzerTokenDao = benutzerTokenDao;
        this.benutzerDao = benutzerDao;
        this.emailService = emailService;
        this.konfiguration = konfiguration;
    }

    @Override
    public boolean sendePasswortZuruecksetzenEmail(String emailadresse) throws BenutzerverwaltungBusinessException {
        BenutzerDaten benutzerDaten = holeBenutzerDaten(emailadresse);
        if(benutzerDaten == null) {
            return false;
        }

        loescheAbgelaufeneTokens(benutzerDaten);
        String token = speichereNeuesToken(benutzerDaten);

        Message message = emailService.erzeugeEmailMitToken(benutzerDaten.getEmailAdresse(), token);
        emailService.sendeEmail(message);
        return true;
    }

    private BenutzerDaten holeBenutzerDaten(String emailadresse) throws BenutzerverwaltungValidationException {
        BenutzerSuchkriterien suchkriterien = new BenutzerSuchkriterien();
        suchkriterien.setEmail(emailadresse);
        Suchergebnis<BenutzerDaten> benutzerListe =
                benutzerverwaltung.sucheBenutzer(
                        suchkriterien,
                        null,
                        null);
        if(benutzerListe.getAnzahlTreffer() < 1) {
            return null;
        }
        return benutzerListe.getTrefferliste().get(0);
    }

    /**
     * Löscht alle Tokens des Benutzers und alle Tokens die abgelaufen sind.
     *
     * @param benutzerDaten Die Daten des Benutzers.
     */
    private void loescheAbgelaufeneTokens(BenutzerDaten benutzerDaten) {
        benutzerTokenDao.loescheTokensFuerBenutzer(benutzerDaten.getBenutzername());
        benutzerTokenDao.loescheTokensVorDatum(new Date());
    }

    /**
     * Speichert neues Token.
     *
     * @param benutzerDaten Die Daten des Benutzers.
     * @return Das neue Token.
     */
    private String speichereNeuesToken(BenutzerDaten benutzerDaten) {
        BenutzerToken benutzerToken = new BenutzerToken();
        benutzerToken.setToken(UUID.randomUUID().toString());

        Long benutzerId = benutzerDaten.getId();
        Benutzer benutzer = benutzerDao.sucheMitId(benutzerId);
        benutzerToken.setBenutzer(benutzer);
        int abgelaufenInStunden =
                konfiguration.getAsInteger(
                        KonfigurationsSchluessel.CONF_SELFSERVICE_TOKEN_ABGELAUFEN_IN_STUNDEN,
                        SELFSERVICE_TOKEN_DEFAULT_DAUER_STUNDEN);
        Date date = Date.from(Instant.now().plus(abgelaufenInStunden, ChronoUnit.HOURS));
        benutzerToken.setAblaufDatum(date);
        benutzerTokenDao.speichere(benutzerToken);
        return benutzerToken.getToken();
    }

    @Override
    public String holeBenutzernameZuToken(String token)
            throws BenutzerverwaltungBusinessException {

        BenutzerToken benutzerToken = benutzerTokenDao.sucheMitId(token);

        if(benutzerToken == null) {
            throw new BenutzerverwaltungBusinessException(FehlerSchluessel.MSG_TOKEN_NICHT_GEFUNDEN);
        }

        if(benutzerToken.getAblaufDatum().toInstant().isBefore(Instant.now())) {
           throw new BenutzerverwaltungBusinessException(FehlerSchluessel.MSG_TOKEN_DATUM_ABGELAUFEN);
        }

        return benutzerToken.getBenutzer().getBenutzername();

    }

    @Override
    public BenutzerDaten setzePasswortZurueck(PasswortZuruecksetzen passwortZuruecksetzen)
            throws BenutzerverwaltungBusinessException {
        BenutzerDaten benutzerDaten = benutzerverwaltung.setzePasswortZurueck(passwortZuruecksetzen);

        benutzerTokenDao.loescheTokensFuerBenutzer(passwortZuruecksetzen.getBenutzername());

        return benutzerDaten;
    }
}
