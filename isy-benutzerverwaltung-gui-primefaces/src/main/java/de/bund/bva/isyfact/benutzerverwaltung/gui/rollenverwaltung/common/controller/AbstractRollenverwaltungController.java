package de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.common.controller;

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


import de.bund.bva.isyfact.benutzerverwaltung.gui.common.controller.AbstractGuiController;
import de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.awkwrapper.RollenverwaltungAwkWrapper;
import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;
import de.bund.bva.isyfact.common.web.global.MessageController;
import org.springframework.context.MessageSource;

/**
 * Diese Klasse bietet, neben allgemeinen Funktionalität Zugriff auf den AWK-Wrapper der Komponente
 * Rollenverwaltung.
 *
 * @author msg systems, Stefan Dellmuth
 */
public abstract class AbstractRollenverwaltungController<T extends AbstractMaskenModel>
    extends AbstractGuiController<T> {

    private final RollenverwaltungAwkWrapper awkWrapper;

    protected AbstractRollenverwaltungController(MessageController messageController,
        MessageSource messageSource, RollenverwaltungAwkWrapper awkWrapper) {
        super(messageController, messageSource);
        this.awkWrapper = awkWrapper;
    }

    protected RollenverwaltungAwkWrapper getAwkWrapper() {
        return awkWrapper;
    }

}
