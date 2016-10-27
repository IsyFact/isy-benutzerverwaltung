package de.bund.bva.isyfact.benutzerverwaltung.core;

import de.bund.bva.isyfact.benutzerverwaltung.persistence.TestPersistenceConfiguration;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ResourceBundleMessageSource;

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

}
