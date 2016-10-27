package de.bund.bva.isyfact.benutzerverwaltung;

import de.bund.bva.pliscommon.konfiguration.common.Konfiguration;
import de.bund.bva.pliscommon.konfiguration.common.impl.ReloadablePropertyKonfiguration;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.support.SimpleThreadScope;

/**
 * Enthält die Konfiguration für einen Testfall.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
@Configuration
@Import(TestPersistenceConfiguration.class)
@ImportResource(
    { "classpath:resources/isy-benutzerverwaltung/spring/isy-benutzerverwaltung-sicherheit-modul.xml",
        "classpath:resources/isy-benutzerverwaltung/spring/isy-benutzerverwaltung-core-modul.xml" })
public class TestfallKonfiguration {

    private static final String[] BASENAMES = { "resources/isy-benutzerverwaltung/nachrichten/fehler",
        "resources/isy-benutzerverwaltung/nachrichten/hinweise",
        "resources/isy-benutzerverwaltung/nachrichten/validation" };

    @Bean
    public static CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer customScopeConfigurer = new CustomScopeConfigurer();
        customScopeConfigurer.addScope("session", new SimpleThreadScope());
        customScopeConfigurer.addScope("request", new SimpleThreadScope());
        return customScopeConfigurer;
    }

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
        String[] konfigurationsDateien = { "/config/isy-benutzerverwaltung.properties" };
        return new ReloadablePropertyKonfiguration(konfigurationsDateien);
    }

}
