package de.bund.bva.isyfact.benutzerverwaltung.gui;

/*-
 * #%L
 * IsyFact Benutzerverwaltung GUI mit Tomahawk
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

import de.bund.bva.isyfact.common.web.exception.common.impl.AusnahmeIdMapperImpl;
import de.bund.bva.isyfact.common.web.global.MessageController;
import de.bund.bva.pliscommon.konfiguration.common.Konfiguration;
import de.bund.bva.pliscommon.konfiguration.common.impl.ReloadablePropertyKonfiguration;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.SimpleThreadScope;

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
