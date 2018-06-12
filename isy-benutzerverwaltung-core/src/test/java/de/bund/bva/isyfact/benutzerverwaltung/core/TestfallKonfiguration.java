package de.bund.bva.isyfact.benutzerverwaltung.core;

/*-
 * #%L
 * IsyFact Benutzerverwaltung Core
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

import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.KonfigurationsSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.passwortpolicy.impl.DefaultPasswortPolicy;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.TestPersistenceConfiguration;
import de.bund.bva.pliscommon.konfiguration.common.Konfiguration;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ResourceBundleMessageSource;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Enthält die Konfiguration für einen Testfall.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
@Configuration
@Import(TestPersistenceConfiguration.class)
@ImportResource("classpath:resources/isy-benutzerverwaltung/spring/isy-benutzerverwaltung-core-modul.xml")
public class TestfallKonfiguration {

    private static final String[] BASENAMES = { "resources/isy-benutzerverwaltung/nachrichten/fehler",
        "resources/isy-benutzerverwaltung/nachrichten/hinweise",
        "resources/isy-benutzerverwaltung/nachrichten/validation" };

    public static Konfiguration konfiguration = mock(Konfiguration.class);

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(BASENAMES);
        return messageSource;
    }

    @Bean
    public MessageSourceHolder messageSourceHolder() {
        return new MessageSourceHolder();
    }

    @Bean
    public Konfiguration konfiguration() {
        when(konfiguration.getAsString(KonfigurationsSchluessel.PASSWORT_POLICY_SONDERZEICHEN,
            DefaultPasswortPolicy.DEFAULT_SONDERZEICHEN))
            .thenReturn(DefaultPasswortPolicy.DEFAULT_SONDERZEICHEN);
        when(konfiguration.getAsInteger(KonfigurationsSchluessel.PASSWORT_POLICY_MINDESTLAENGE,
            DefaultPasswortPolicy.DEFAULT_MINDESTLAENGE))
            .thenReturn(DefaultPasswortPolicy.DEFAULT_MINDESTLAENGE);
        when(konfiguration.getAsInteger(KonfigurationsSchluessel.PASSWORT_POLICY_ANZAHL_LETZTE_PASSWOERTER,
            DefaultPasswortPolicy.DEFAULT_ANZAHL_LETZTE_PASSWOERTER))
            .thenReturn(DefaultPasswortPolicy.DEFAULT_ANZAHL_LETZTE_PASSWOERTER);

        return konfiguration;
    }

}
