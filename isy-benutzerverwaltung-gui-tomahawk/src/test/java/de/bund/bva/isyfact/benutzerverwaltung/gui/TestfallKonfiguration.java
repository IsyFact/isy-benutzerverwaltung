package de.bund.bva.isyfact.benutzerverwaltung.gui;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.SimpleThreadScope;

import de.bund.bva.isyfact.common.web.exception.common.impl.AusnahmeIdMapperImpl;
import de.bund.bva.isyfact.common.web.global.MessageController;
import de.bund.bva.pliscommon.konfiguration.common.Konfiguration;
import de.bund.bva.pliscommon.konfiguration.common.impl.ReloadablePropertyKonfiguration;

/**
 * Enthält die Konfiguration für einen Testfall.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
@Configuration
@Import(TestPersistenceConfiguration.class)
@ImportResource(
    { "classpath:resources/isy-benutzerverwaltung/spring/isy-benutzerverwaltung-gui-tomahawk-modul.xml",
        "classpath:resources/isy-benutzerverwaltung/spring/isy-benutzerverwaltung-sicherheit-modul.xml",
        "classpath:resources/isy-benutzerverwaltung/spring/isy-benutzerverwaltung-core-modul.xml" })
public class TestfallKonfiguration {

    @Bean
    public static CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer customScopeConfigurer = new CustomScopeConfigurer();
        customScopeConfigurer.addScope("thread", new SimpleThreadScope());
        return customScopeConfigurer;
    }

    @Bean
    public Konfiguration konfiguration() {
        String[] konfigurationsDateien = { "/config/isy-benutzerverwaltung.properties" };
        return new ReloadablePropertyKonfiguration(konfigurationsDateien);
    }

    @Bean
    public MessageController messageController() {
        MessageController messageController = new MessageController();
        messageController.setAusnahmeIdMapper(new AusnahmeIdMapperImpl("BNVW99999"));
        return messageController;
    }
}