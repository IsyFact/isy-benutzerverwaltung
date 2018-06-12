package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.passwordpolicy.impl;

import java.util.ArrayList;
import java.util.List;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungTechnicalRuntimeException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.KonfigurationsSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.ValidierungSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.Benutzerverwaltung;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.passwortpolicy.ValidationResult;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.passwortpolicy.impl.DefaultPasswortPolicy;
import de.bund.bva.pliscommon.konfiguration.common.Konfiguration;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultPasswortPolicyTest {

    private static final String GUELTIGES_PASSWORT = "qwertzuiO1!";

    @Mock
    private Benutzerverwaltung benutzerverwaltung;

    @Mock
    private Konfiguration konfiguration;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private BenutzerDaten testUser;

    @Before
    public void setup() throws Exception {
        setupMessageSourceForExceptionMessage();

        when(konfiguration.getAsInteger(KonfigurationsSchluessel.PASSWORT_POLICY_MINDESTLAENGE,
            DefaultPasswortPolicy.DEFAULT_MINDESTLAENGE))
            .thenReturn(DefaultPasswortPolicy.DEFAULT_MINDESTLAENGE);
        when(konfiguration.getAsString(KonfigurationsSchluessel.PASSWORT_POLICY_SONDERZEICHEN,
            DefaultPasswortPolicy.DEFAULT_SONDERZEICHEN))
            .thenReturn(DefaultPasswortPolicy.DEFAULT_SONDERZEICHEN);
        when(konfiguration.getAsInteger(KonfigurationsSchluessel.PASSWORT_POLICY_ANZAHL_LETZTE_PASSWOERTER,
            DefaultPasswortPolicy.DEFAULT_ANZAHL_LETZTE_PASSWOERTER))
            .thenReturn(DefaultPasswortPolicy.DEFAULT_ANZAHL_LETZTE_PASSWOERTER);
        when(benutzerverwaltung.leseBenutzer("gibtsnicht"))
            .thenThrow(BenutzerverwaltungBusinessException.class);

        testUser = setupTestnutzer();

        when(benutzerverwaltung.leseBenutzer(testUser.getBenutzername())).thenReturn(testUser);
    }

    private BenutzerDaten setupTestnutzer() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        List<String> altePasswoerter = new ArrayList<>();

        altePasswoerter.add(passwordEncoder.encode(GUELTIGES_PASSWORT + "1"));
        altePasswoerter.add(passwordEncoder.encode(GUELTIGES_PASSWORT + "2"));

        BenutzerDaten benutzer = new BenutzerDaten();

        benutzer.setLetztePasswoerter(altePasswoerter);
        benutzer.setBenutzername("testuser");

        return benutzer;
    }

    private void setupMessageSourceForExceptionMessage() {
        MessageSource messageSource = new ResourceBundleMessageSource();
        ((ResourceBundleMessageSource) messageSource)
            .setBasename("resources.isy-benutzerverwaltung.nachrichten.validation");
        MessageSourceHolder messageSourceHolder = new MessageSourceHolder();
        messageSourceHolder.setMessageSource(messageSource);
    }

    @Test
    public void validatePasswortGueltig() {
        DefaultPasswortPolicy passwortPolicy =
            new DefaultPasswortPolicy(benutzerverwaltung, konfiguration, passwordEncoder);

        ValidationResult result = passwortPolicy.validate(GUELTIGES_PASSWORT, testUser.getBenutzername());

        assertTrue(result.isValid());
    }

    @Test
    public void validatePasswortGueltigBenutzernameNull() {
        DefaultPasswortPolicy passwortPolicy =
            new DefaultPasswortPolicy(benutzerverwaltung, konfiguration, passwordEncoder);

        ValidationResult result = passwortPolicy.validate(GUELTIGES_PASSWORT, null);

        assertTrue(result.isValid());
    }

    @Test
    public void validateMindestlaenge() {
        DefaultPasswortPolicy passwortPolicy =
            new DefaultPasswortPolicy(benutzerverwaltung, konfiguration, passwordEncoder);

        ValidationResult result = passwortPolicy.validate("zukurz", testUser.getBenutzername());

        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains(MessageSourceHolder
            .getMessage(ValidierungSchluessel.MSG_PASSWORT_POLICY_DEFAULT_LAENGE,
                Integer.toString(DefaultPasswortPolicy.DEFAULT_MINDESTLAENGE))));
    }


    @Test
    public void validateUngueltigeZeichen() {
        DefaultPasswortPolicy passwortPolicy =
            new DefaultPasswortPolicy(benutzerverwaltung, konfiguration, passwordEncoder);

        ValidationResult result =
            passwortPolicy.validate(GUELTIGES_PASSWORT + "~", testUser.getBenutzername());

        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains(MessageSourceHolder
            .getMessage(ValidierungSchluessel.MSG_PASSWORT_POLICY_DEFAULT_UNGUELTIGE_ZEICHEN)));
    }

    @Test
    public void validateKleinbuchstaben() {
        DefaultPasswortPolicy defaultPasswortPolicy =
            new DefaultPasswortPolicy(benutzerverwaltung, konfiguration, passwordEncoder);

        ValidationResult result = defaultPasswortPolicy.validate("QWERTZU1!", testUser.getBenutzername());

        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains(MessageSourceHolder
            .getMessage(ValidierungSchluessel.MSG_PASSWORT_POLICY_DEFAULT_KLEINBUCHSTABEN)));
    }

    @Test
    public void validateGrossbuchstaben() {
        DefaultPasswortPolicy defaultPasswortPolicy =
            new DefaultPasswortPolicy(benutzerverwaltung, konfiguration, passwordEncoder);

        ValidationResult result = defaultPasswortPolicy.validate("qwertzu1!", testUser.getBenutzername());

        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains(MessageSourceHolder
            .getMessage(ValidierungSchluessel.MSG_PASSWORT_POLICY_DEFAULT_GROSSBUCHSTABEN)));
    }

    @Test
    public void validateSonderzeichen() {
        DefaultPasswortPolicy defaultPasswortPolicy =
            new DefaultPasswortPolicy(benutzerverwaltung, konfiguration, passwordEncoder);

        ValidationResult result = defaultPasswortPolicy.validate("qwertzU1", testUser.getBenutzername());

        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains(MessageSourceHolder
            .getMessage(ValidierungSchluessel.MSG_PASSWORT_POLICY_DEFAULT_SONDERZEICHEN,
                DefaultPasswortPolicy.DEFAULT_SONDERZEICHEN)));
    }

    @Test
    public void validateZahl() {
        DefaultPasswortPolicy defaultPasswortPolicy =
            new DefaultPasswortPolicy(benutzerverwaltung, konfiguration, passwordEncoder);

        ValidationResult result = defaultPasswortPolicy.validate("qwertzU!", testUser.getBenutzername());

        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains(
            MessageSourceHolder.getMessage(ValidierungSchluessel.MSG_PASSWORT_POLICY_DEFAULT_ZAHL)));
    }

    @Test
    public void validateLetztePasswoerter() throws BenutzerverwaltungBusinessException {
        DefaultPasswortPolicy defaultPasswortPolicy =
            new DefaultPasswortPolicy(benutzerverwaltung, konfiguration, passwordEncoder);

        ValidationResult result =
            defaultPasswortPolicy.validate(GUELTIGES_PASSWORT + "1", testUser.getBenutzername());

        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains(MessageSourceHolder
            .getMessage(ValidierungSchluessel.MSG_PASSWORT_POLICY_DEFAULT_LETZTE_PASSWOERTER,
                Integer.toString(DefaultPasswortPolicy.DEFAULT_ANZAHL_LETZTE_PASSWOERTER))));
    }

    @Test(expected = BenutzerverwaltungTechnicalRuntimeException.class)
    public void validateLetztePasswoerterBenutzerNichtVorhanden() {
        setupMessageSourceForExceptionMessage();

        DefaultPasswortPolicy defaultPasswortPolicy =
            new DefaultPasswortPolicy(benutzerverwaltung, konfiguration, passwordEncoder);

        ValidationResult result = defaultPasswortPolicy.validate(GUELTIGES_PASSWORT, "gibtsnicht");
    }
}