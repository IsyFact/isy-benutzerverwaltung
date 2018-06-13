package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.passwortpolicy.impl;

import java.util.function.Predicate;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungTechnicalRuntimeException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.KonfigurationsSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.ValidierungSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.Benutzerverwaltung;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.passwortpolicy.PasswortPolicy;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.passwortpolicy.ValidationResult;
import de.bund.bva.pliscommon.konfiguration.common.Konfiguration;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Default Password Policy
 *
 * - Das Passwort muss aus mindestens 8 Zeichen bestehen (konfigurierbar)
 * - Es sind sowohl Groß als auch Kleinbuchstaben zu verwenden
 * - Das Passwort muss mindestens eine Zahl und ein zugelassenes Sonderzeichen (!#&*+,-_.;:?) enthalten (konfigurierbar)
 * - Die letzten 10 Passwörter dürfen nicht verwendet werden. (konfigurierbar)
 */
public class DefaultPasswortPolicy implements PasswortPolicy {

    public static final String DEFAULT_SONDERZEICHEN = "!#&*+,-_.;:?";

    public static final int DEFAULT_MINDESTLAENGE = 8;

    public static final int DEFAULT_ANZAHL_LETZTE_PASSWOERTER = 10;

    private final Benutzerverwaltung benutzerverwaltung;

    private final Konfiguration konfiguration;

    private final PasswordEncoder passwordEncoder;

    public DefaultPasswortPolicy(Benutzerverwaltung benutzerverwaltung, Konfiguration konfiguration,
        PasswordEncoder passwordEncoder) {
        this.benutzerverwaltung = benutzerverwaltung;
        this.konfiguration = konfiguration;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ValidationResult validate(String passwort, String benutzername) {
        if (!hatMindestlaenge(passwort)) {
            int mindestlaenge = konfiguration
                .getAsInteger(KonfigurationsSchluessel.PASSWORT_POLICY_MINDESTLAENGE, DEFAULT_MINDESTLAENGE);
            String message = MessageSourceHolder
                .getMessage(ValidierungSchluessel.MSG_PASSWORT_POLICY_DEFAULT_LAENGE,
                    Integer.toString(mindestlaenge));

            return new ValidationResult(false, message);
        } else if (!hatGueltigeZeichen(passwort)) {
            String message = MessageSourceHolder
                .getMessage(ValidierungSchluessel.MSG_PASSWORT_POLICY_DEFAULT_UNGUELTIGE_ZEICHEN);

            return new ValidationResult(false, message);
        } else if (!enthaeltKleinbuchstabe(passwort)) {
            String message = MessageSourceHolder
                .getMessage(ValidierungSchluessel.MSG_PASSWORT_POLICY_DEFAULT_KLEINBUCHSTABEN);

            return new ValidationResult(false, message);
        } else if (!enthaeltGrossbuchstabe(passwort)) {
            String message = MessageSourceHolder
                .getMessage(ValidierungSchluessel.MSG_PASSWORT_POLICY_DEFAULT_GROSSBUCHSTABEN);

            return new ValidationResult(false, message);
        } else if (!enthaeltSonderzeichen(passwort)) {
            String erlaubteSonderzeichen = konfiguration
                .getAsString(KonfigurationsSchluessel.PASSWORT_POLICY_SONDERZEICHEN, DEFAULT_SONDERZEICHEN);
            String message = MessageSourceHolder
                .getMessage(ValidierungSchluessel.MSG_PASSWORT_POLICY_DEFAULT_SONDERZEICHEN,
                    erlaubteSonderzeichen);

            return new ValidationResult(false, message);
        } else if (!enthaeltZahl(passwort)) {
            String message =
                MessageSourceHolder.getMessage(ValidierungSchluessel.MSG_PASSWORT_POLICY_DEFAULT_ZAHL);

            return new ValidationResult(false, message);
        } else if (benutzername != null && !enthaeltNichtDieLetztenPasswoerter(passwort, benutzername)) {
            int anzahlLetztePasswoerter = konfiguration
                .getAsInteger(KonfigurationsSchluessel.PASSWORT_POLICY_ANZAHL_LETZTE_PASSWOERTER,
                    DEFAULT_ANZAHL_LETZTE_PASSWOERTER);
            String message = MessageSourceHolder
                .getMessage(ValidierungSchluessel.MSG_PASSWORT_POLICY_DEFAULT_LETZTE_PASSWOERTER,
                    Integer.toString(anzahlLetztePasswoerter));

            return new ValidationResult(false, message);
        } else {
            return new ValidationResult(true, "");
        }
    }

    private boolean hatGueltigeZeichen(String password) {
        String sonderzeichen = konfiguration
            .getAsString(KonfigurationsSchluessel.PASSWORT_POLICY_SONDERZEICHEN, DEFAULT_SONDERZEICHEN);

        Predicate<Character> gueltigeZeichen =
            c -> Character.isLowerCase(c) || Character.isUpperCase(c) || Character.isDigit(c) || sonderzeichen
                .contains(c.toString());

        return password.chars().mapToObj(i -> (char) i).allMatch(gueltigeZeichen);
    }

    private boolean hatMindestlaenge(String password) {
        return password.length() >= konfiguration
            .getAsInteger(KonfigurationsSchluessel.PASSWORT_POLICY_MINDESTLAENGE, DEFAULT_MINDESTLAENGE);
    }

    private boolean enthaeltKleinbuchstabe(String password) {
        return password.chars().mapToObj(i -> (char) i).anyMatch(Character::isLowerCase);
    }

    private boolean enthaeltGrossbuchstabe(String password) {
        return password.chars().mapToObj(i -> (char) i).anyMatch(Character::isUpperCase);
    }

    private boolean enthaeltZahl(String password) {
        return password.chars().mapToObj(i -> (char) i).anyMatch(Character::isDigit);
    }

    private boolean enthaeltSonderzeichen(String password) {
        String sonderzeichen = konfiguration
            .getAsString(KonfigurationsSchluessel.PASSWORT_POLICY_SONDERZEICHEN, DEFAULT_SONDERZEICHEN);

        return password.chars().mapToObj(i -> (char) i).anyMatch(c -> sonderzeichen.contains(c.toString()));
    }

    private boolean enthaeltNichtDieLetztenPasswoerter(String passwort, String benutzername) {
        BenutzerDaten benutzerDaten;

        try {
            benutzerDaten = benutzerverwaltung.leseBenutzer(benutzername);
        } catch (BenutzerverwaltungBusinessException e) {
            throw new BenutzerverwaltungTechnicalRuntimeException(
                ValidierungSchluessel.MSG_BENUTZERNAME_NICHT_VORHANDEN, benutzername);
        }

        int anzahlLetztePasswoerter = konfiguration
            .getAsInteger(KonfigurationsSchluessel.PASSWORT_POLICY_ANZAHL_LETZTE_PASSWOERTER,
                DEFAULT_ANZAHL_LETZTE_PASSWOERTER);

        int endIndex = benutzerDaten.getLetztePasswoerter().size();
        int startIndex = Math.max(endIndex - anzahlLetztePasswoerter, 0);

        return benutzerDaten.getLetztePasswoerter().subList(startIndex, endIndex).stream()
            .noneMatch(altesPasswort -> passwordEncoder.matches(passwort, altesPasswort));
    }
}
