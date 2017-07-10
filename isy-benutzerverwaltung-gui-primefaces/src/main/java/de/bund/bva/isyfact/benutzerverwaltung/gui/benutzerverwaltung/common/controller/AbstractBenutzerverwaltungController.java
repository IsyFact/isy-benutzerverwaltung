package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.controller;

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


import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.BenutzerverwaltungAwkWrapper;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.controller.AbstractGuiController;
import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;
import de.bund.bva.isyfact.common.web.global.MessageController;
import org.springframework.context.MessageSource;

/**
 * Diese Klasse bietet einen zentralen Zugriff für alle Controller auf den AwkWrapper und implementiert
 * Standard-Funktionen.
 *
 * @author Capgemini, Jonas Zitz
 * @version $Id: AbstractSelfServiceController.java 41870 2013-07-25 13:54:34Z jozitz $
 */
public abstract class AbstractBenutzerverwaltungController<T extends AbstractMaskenModel>
    extends AbstractGuiController<T> {

    private final BenutzerverwaltungAwkWrapper awkWrapper;

    protected AbstractBenutzerverwaltungController(MessageController messageController,
        MessageSource messageSource, BenutzerverwaltungAwkWrapper awkWrapper) {
        super(messageController, messageSource);
        this.awkWrapper = awkWrapper;
    }

    protected BenutzerverwaltungAwkWrapper getAwkWrapper() {
        return awkWrapper;
    }

}
