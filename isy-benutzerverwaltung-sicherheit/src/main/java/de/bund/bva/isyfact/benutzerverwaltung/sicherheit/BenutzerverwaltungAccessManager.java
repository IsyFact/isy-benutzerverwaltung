package de.bund.bva.isyfact.benutzerverwaltung.sicherheit;

/*-
 * #%L
 * IsyFact Benutzerverwaltung Sicherheit
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.FehlerSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.KonfigurationsSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.Benutzerverwaltung;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Benutzer;
import de.bund.bva.isyfact.benutzerverwaltung.sicherheit.exception.BenutzerverwaltungAuthentifizierungFehlgeschlagenException;
import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.bund.bva.pliscommon.konfiguration.common.Konfiguration;
import de.bund.bva.pliscommon.sicherheit.accessmgr.AccessManager;
import de.bund.bva.pliscommon.sicherheit.common.exception.AuthentifizierungFehlgeschlagenException;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementiert den Access Managers mit Hilfe der IsyFact Benutzerverwaltung.
 *
 * @author msg systems ag, Dirk Jäger
 * @author msg systems ag, Andreas Schubert
 * @author msg systems ag, Stefan Dellmuth
 */

public class BenutzerverwaltungAccessManager implements
    AccessManager<BenutzerverwaltungAufrufKontextImpl, BenutzerverwaltungAuthentifizierungErgebnis> {

    private static final IsyLogger LOG = IsyLoggerFactory.getLogger(BenutzerverwaltungAccessManager.class);

    private static final int MAX_FEHLERVERSUCHE_DEFAULT = 5;

    private static final int BENUTZER_ABLAUFFRIST = 365;

    private final Benutzerverwaltung benutzerverwaltung;

    private final PasswordEncoder passwordEncoder;

    private final Konfiguration konfiguration;

    public BenutzerverwaltungAccessManager(Benutzerverwaltung benutzerverwaltung,
        PasswordEncoder passwordEncoder, Konfiguration konfiguration) {
        this.benutzerverwaltung = benutzerverwaltung;
        this.passwordEncoder = passwordEncoder;
        this.konfiguration = konfiguration;
    }

    @Override
    @Transactional(noRollbackFor = AuthentifizierungFehlgeschlagenException.class)
    public BenutzerverwaltungAuthentifizierungErgebnis authentifiziere(
        BenutzerverwaltungAufrufKontextImpl aufrufkontext) {

        String kennung = aufrufkontext.getDurchfuehrenderBenutzerKennung();
        String passwort = aufrufkontext.getDurchfuehrenderBenutzerPasswort();
        boolean passwortIstHash = aufrufkontext.isPasswortIstHash();

        try {
            BenutzerDaten authentifizierterBenutzer =
                authentifiziereBenutzer(kennung, passwort, passwortIstHash);
            return fuelleAuthentifizierungsergebnis(authentifizierterBenutzer);
        } catch (BenutzerverwaltungAuthentifizierungFehlgeschlagenException e) {
            throw new AuthentifizierungFehlgeschlagenException(e.getFehlertext());
        }
    }

    /**
     * @param authentifiziereBenutzer den aus der Benutzerverwaltungsschnittstelle zurückgegebenen Benutzer
     * @return das Ergebnis der Authentifizierung
     */
    private BenutzerverwaltungAuthentifizierungErgebnis fuelleAuthentifizierungsergebnis(
        BenutzerDaten authentifiziereBenutzer) {
        BenutzerverwaltungAuthentifizierungErgebnis ergebnis;
        ergebnis = new BenutzerverwaltungAuthentifizierungErgebnis();
        ergebnis.setBenutzername(authentifiziereBenutzer.getBenutzername());
        ergebnis.setNachname(authentifiziereBenutzer.getNachname());
        ergebnis.setVorname(authentifiziereBenutzer.getVorname());
        ergebnis.setBehoerde(authentifiziereBenutzer.getBehoerde());
        ergebnis.setEmailAdresse(authentifiziereBenutzer.getEmailAdresse());
        ergebnis.setPasswort(authentifiziereBenutzer.getPasswort());
        ergebnis.setRollenIds(ermittleRollenIds(authentifiziereBenutzer));

        return ergebnis;
    }

    /**
     * Ermittelt die IDs der Rollen eines Benutzers.
     *
     * @param benutzer Benutzer
     * @return eine Listen mit den IDs der Rollen.
     */
    private List<String> ermittleRollenIds(BenutzerDaten benutzer) {
        List<String> rollenIds = new ArrayList<>(benutzer.getRollen().size());

        for (RolleDaten rolle : benutzer.getRollen()) {
            rollenIds.add(rolle.getRollenId());
        }

        return rollenIds;
    }

    @Override
    public void befuelleAufrufkontext(BenutzerverwaltungAufrufKontextImpl aufrufkontext,
        BenutzerverwaltungAuthentifizierungErgebnis ergebnis) {

        // befüllt den übergebenen Aufrufkontext mit den Ergebnissen
        // einer vorher stattgefundenen Authentifzierung, die in
        // einem entsprechenden Ergebnis-Objekt hinter legt sind.
        aufrufkontext.setDurchfuehrenderBenutzerKennung(ergebnis.getBenutzername());
        aufrufkontext.setDurchfuehrendeBehoerde(ergebnis.getBehoerde());
        aufrufkontext
            .setDurchfuehrenderSachbearbeiterName(ergebnis.getVorname() + " " + ergebnis.getNachname());
        aufrufkontext.setDurchfuehrenderBenutzerPasswort(ergebnis.getPasswort());
        aufrufkontext.setPasswortIstHash(true);

        aufrufkontext.setRolle(ergebnis.getRollenIds().toArray(new String[ergebnis.getRollenIds().size()]));
        aufrufkontext.setRollenErmittelt(true);
    }

    @Override
    public Object erzeugeCacheSchluessel(BenutzerverwaltungAufrufKontextImpl aufrufkontext) {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(aufrufkontext.getDurchfuehrenderBenutzerKennung());
        builder.append(aufrufkontext.getDurchfuehrenderBenutzerPasswort());
        builder.append(aufrufkontext.getDurchfuehrendeBehoerde());
        builder.append(aufrufkontext.isRollenErmittelt());
        builder.append(aufrufkontext.isPasswortIstHash());
        for (String rolle : aufrufkontext.getRolle()) {
            builder.append(rolle);
        }

        return builder.build();
    }

    @Override
    public void logout(BenutzerverwaltungAuthentifizierungErgebnis aufrufkontext) {
        // Diese Methode wird beim Anmelden(!) ausgeführt. Zu diesem Zeitpunkt muss hier nichts getan werden.
    }

    @Override
    public boolean pingAccessManager() {
        // Die Benutzerverwaltung ist lokal und immer erreichbar.
        return true;
    }

    @Override
    public boolean pingAccessManagerByLoginLogout(BenutzerverwaltungAufrufKontextImpl aufrufkontext) {
        // Die Benutzerverwaltung ist lokal und immer erreichbar.
        return true;
    }

    /**
     * Diese Methode authentifiziert einen {@link Benutzer} gegenueber dem Benutzerverzeichnis. Bei
     * erfolgreicher Authentifizierung das {@link Benutzer Benutzer-Objekt} zurueckgeliefert. Dieses bietet
     * {@link Benutzer#getRollen() Rollen}, die zur Nutzung weiterer Funktionalitaet autorisieren.
     *
     * @param benutzername    ist der {@link Benutzer#getBenutzername() Benutzername} des zu
     *                        authentifizierenden Benutzers.
     * @param passwort        ist das {@link Benutzer#getPasswort() Passwort} des zu authentifizierenden
     *                        Benutzers.
     * @param passwortIstHash gibt mit {@link Boolean#TRUE} an, dass das {@link Benutzer#getPasswort()
     *                        Passwort} gehashed ist, {@link Boolean#FALSE} ansonsten.
     * @return das {@link Benutzer}-Objekt nach erfolgreicher Authentifizierung
     * @throws BenutzerverwaltungAuthentifizierungFehlgeschlagenException im Fall einer fehlgeschlagenen
     *                                                                    Authentifizierung
     */
    private BenutzerDaten authentifiziereBenutzer(String benutzername, String passwort,
        boolean passwortIstHash) throws BenutzerverwaltungAuthentifizierungFehlgeschlagenException {

        pruefeBenutzerUndPasswort(benutzername, passwort);

        BenutzerDaten benutzer = leseBenutzer(benutzername);

        pruefeBenutzerStatus(benutzer);

        if (benutzerAbgelaufen(benutzer)) {
            sperreBenutzer(benutzer);
            setzeAnmeldedatumZurueck(benutzer);
            throw new BenutzerverwaltungAuthentifizierungFehlgeschlagenException(
                FehlerSchluessel.MSG_BENUTZER_ABGELAUFEN);
        }

        if (passwortKorrekt(passwort, benutzer, passwortIstHash)) {
            LOG.debug("Benutzer \"{}\" erfolgreich authentifiziert.", benutzername);
            return authentifizierungErfolgreichBehandlung(benutzer, passwortIstHash);
        } else {
            authentifizierungFehlgeschlagenBehandlung(benutzer);
            throw new BenutzerverwaltungAuthentifizierungFehlgeschlagenException(
                FehlerSchluessel.MSG_BENUTZER_PASSWORT_FALSCH);
        }
    }

    private void setzeAnmeldedatumZurueck(BenutzerDaten benutzer) {
        try {
            benutzerverwaltung.speichereErfolgreicheAnmeldung(benutzer.getBenutzername());
        } catch (BenutzerverwaltungBusinessException exception) {
            LOG.error("Konnte Anmeldedatum nicht zurücksetzen", exception);
        }
    }

    private void pruefeBenutzerUndPasswort(String benutzername, String passwort)
        throws BenutzerverwaltungAuthentifizierungFehlgeschlagenException {
        if (benutzername == null || benutzername.isEmpty() || passwort == null || passwort.isEmpty()) {
            LOG.debug(
                "Authentifizierung fehlgeschlagen, da Benutzername, Passwort oder PasswortHash null bzw. leer.");
            throw new BenutzerverwaltungAuthentifizierungFehlgeschlagenException(
                FehlerSchluessel.MSG_AUTHENTIFIZIERUNG_FEHLGESCHLAGEN);
        }
    }

    private BenutzerDaten leseBenutzer(String benutzername)
        throws BenutzerverwaltungAuthentifizierungFehlgeschlagenException {
        try {
            return benutzerverwaltung.leseBenutzer(benutzername);
        } catch (BenutzerverwaltungBusinessException validationException) {
            LOG.debugFachdaten("Authentifizierung fehlgeschlagen, da der Benutzer \"{}\" nicht existiert.",
                benutzername);
            throw new BenutzerverwaltungAuthentifizierungFehlgeschlagenException(
                FehlerSchluessel.MSG_AUTHENTIFIZIERUNG_FEHLGESCHLAGEN);
        }
    }

    private void pruefeBenutzerStatus(BenutzerDaten benutzer)
        throws BenutzerverwaltungAuthentifizierungFehlgeschlagenException {
        if (BenutzerStatus.AKTIVIERT != benutzer.getStatus()) {
            LOG.debugFachdaten("Authentifizierung fehlgeschlagen, da der Benutzer \"{}\" {} ist.",
                benutzer.getBenutzername(), benutzer.getStatus());
            authentifizierungFehlgeschlagenBehandlung(benutzer);
            throw new BenutzerverwaltungAuthentifizierungFehlgeschlagenException(
                FehlerSchluessel.MSG_BENUTZER_GESPERRT);
        }
    }

    private boolean benutzerAbgelaufen(BenutzerDaten benutzer) {
        Date letzteAnmeldung = benutzer.getLetzteAnmeldung();

        if (letzteAnmeldung == null) {
            return false;
        }

        int ablaufFrist = konfiguration
            .getAsInteger(KonfigurationsSchluessel.ZUGRIFFSVERWALTUNG_BENUTZER_ABLAUFFRIST_IN_TAGEN,
                BENUTZER_ABLAUFFRIST);

        Date jetzt = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(letzteAnmeldung);
        calendar.add(Calendar.DATE, ablaufFrist);
        Date ablaufDatum = calendar.getTime();

        return (jetzt.after(ablaufDatum));
    }

    private boolean passwortKorrekt(String passwort, BenutzerDaten benutzer, boolean passwortIstHash) {
        if (passwortIstHash) {
            return passwort.equals(benutzer.getPasswort());
        } else {
            return passwordEncoder.matches(passwort, benutzer.getPasswort());
        }
    }

    /**
     * Diese Methode setzt die Anzahl an {@link Benutzer#getFehlanmeldeVersuche() fehlgeschlagenen
     * Login-Versuchen} auf 0 zurueck. Zusaetzlich wird das {@link Benutzer#getLetzteAnmeldung() Login-Datum}
     * erfasst.
     *
     * @param benutzer          die Benutzerdaten aus der Datenbank
     * @param istFolgeanmeldung {@link Boolean#TRUE}, falls die Anmeldung mit dem Passwort-Hash als
     *                          Folgeanmeldung durchgefuehrt wird. In dem Fall wird das{@link
     *                          Benutzer#getLetzteAnmeldung() Datum der letzten Anmeldung} aktualisiert und
     *                          die {@link Benutzer#getFehlanmeldeVersuche() Fehlanmeldeversuche} auf 0
     *                          zurueckgesetzt. Die Attribute sollen immer mit der ersten erfolgreichen
     *                          Authentifizierung aktualisiert werden, nicht jedoch bei Folgeanmeldungen mit
     *                          dem Passwort-Hash.
     */
    private BenutzerDaten authentifizierungErfolgreichBehandlung(BenutzerDaten benutzer,
        boolean istFolgeanmeldung) {
        if (istFolgeanmeldung) {
            LOG.debug("Es handelt sich um eine Folgeanmeldung.");
        } else {
            LOG.debugFachdaten(
                "Setze die letzte Anmeldezeit des Benutzers \"{}\" und setze die Fehlanmeldeversuche auf 0 zurueck.",
                benutzer.getBenutzername());
            try {
                return benutzerverwaltung.speichereErfolgreicheAnmeldung(benutzer.getBenutzername());
            } catch (BenutzerverwaltungBusinessException validationException) {
                LOG.error(validationException.getMessage(), validationException);
            }
        }
        return benutzer;
    }

    /**
     * Diese Methode behandelt das Fehlschlagen von Login-Versuchen eines ermittelbaren {@link Benutzer
     * Benutzers}. Ein Benutzerist dann ermittelbar, wenn er dem System aufgrund des eingegebenen {@link
     * Benutzer#getBenutzername() Benutzernames} identifizierbar und somit System-Bekannt ist.
     * <p>
     * Die Methode erhoeht die Anzahl von {@link Benutzer#getFehlanmeldeVersuche() Anmelde-Fehlversuchen}
     * eines {@link Benutzer Benutzers} und sperrt diesen, sobald der {@link
     * KonfigurationsSchluessel#ZUGRIFFSVERWALTUNG_MAX_FEHLANMELDEVERSUCHE konfigurierte Maximalwert} zu
     * fehlgeschlagenen Login-Versuchen ueberschritten wird.
     *
     * @param benutzer die Benutzerdaten aus der Datenbank
     */
    private void authentifizierungFehlgeschlagenBehandlung(BenutzerDaten benutzer) {
        try {
            benutzer = benutzerverwaltung.speichereFehlgeschlageneAnmeldung(benutzer.getBenutzername());
        } catch (BenutzerverwaltungBusinessException validationException) {
            LOG.error("Konnte Benutzer nicht editieren.", validationException);
            return;
        }

        // Nach zu vielen fehlerhaften Anmeldungen wird der Benutzer gesperrt.
        if (benutzer.getFehlanmeldeVersuche() >= konfiguration
            .getAsInteger(KonfigurationsSchluessel.ZUGRIFFSVERWALTUNG_MAX_FEHLANMELDEVERSUCHE,
                MAX_FEHLERVERSUCHE_DEFAULT)) {
            sperreBenutzer(benutzer);
        }

        LOG.debugFachdaten("Die Anmeldung des Benutzers \"{}\" ist zum {}. Mal fehlgeschlagen.",
            benutzer.getBenutzername(), benutzer.getFehlanmeldeVersuche());
    }

    private void sperreBenutzer(BenutzerDaten benutzer) {
        try {
            benutzerverwaltung.setzeStatus(benutzer.getBenutzername(), BenutzerStatus.GESPERRT);
        } catch (BenutzerverwaltungBusinessException exception) {
            LOG.error("Konnte Benutzer nicht sperren.", exception);
        }
    }
}
