package de.bund.bva.isyfact.benutzerverwaltung.core.selfservice.impl;

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
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.BenutzerDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Benutzer;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.selfservice.dao.BenutzerTokenDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.selfservice.entity.BenutzerToken;
import de.bund.bva.pliscommon.konfiguration.common.Konfiguration;

/**
 * Implementiert den Anwendungsfall Passwort Zürucksetzen des Self-Service.
 *
 * @author Björn Saxe, msg systems ag
 */
class AwfPasswortZuruecksetzen {

    private static final int SELFSERVICE_TOKEN_DEFAULT_DAUER_STUNDEN = 48;

    private final Benutzerverwaltung benutzerverwaltung;
    private final BenutzerTokenDao benutzerTokenDao;
    private final BenutzerDao benutzerDao;
    private final EmailService emailService;
    private final Konfiguration konfiguration;

    AwfPasswortZuruecksetzen(
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

    boolean sendePasswortZuruecksetzenEmail(String emailadresse) throws
        BenutzerverwaltungBusinessException {
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

    private BenutzerDaten holeBenutzerDaten(String emailadresse) throws
        BenutzerverwaltungValidationException {
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

    private void loescheAbgelaufeneTokens(BenutzerDaten benutzerDaten) {
        benutzerTokenDao.loescheTokensFuerBenutzer(benutzerDaten.getBenutzername());
        benutzerTokenDao.loescheTokensVorDatum(new Date());
    }

    private String speichereNeuesToken(BenutzerDaten benutzerDaten) {
        BenutzerToken benutzerToken = new BenutzerToken();
        benutzerToken.setToken(UUID.randomUUID().toString());

        Long benutzerId = benutzerDaten.getId();
        Benutzer benutzer = benutzerDao.sucheMitId(benutzerId);
        benutzerToken.setBenutzer(benutzer);
        int abgelaufenInStunden =
            konfiguration.getAsInteger(
                KonfigurationsSchluessel.SELFSERVICE_TOKEN_ABGELAUFEN_IN_STUNDEN,
                SELFSERVICE_TOKEN_DEFAULT_DAUER_STUNDEN);
        Date date = Date.from(Instant.now().plus(abgelaufenInStunden, ChronoUnit.HOURS));
        benutzerToken.setAblaufDatum(date);
        benutzerTokenDao.speichere(benutzerToken);
        return benutzerToken.getToken();
    }

    String holeBenutzernameZuToken(String token)
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

    BenutzerDaten setzePasswortZurueck(PasswortZuruecksetzen passwortZuruecksetzen)
        throws BenutzerverwaltungBusinessException {
        BenutzerDaten benutzerDaten = benutzerverwaltung.setzePasswortZurueck(passwortZuruecksetzen);

        loescheAbgelaufeneTokens(benutzerDaten);

        return benutzerDaten;
    }
}
