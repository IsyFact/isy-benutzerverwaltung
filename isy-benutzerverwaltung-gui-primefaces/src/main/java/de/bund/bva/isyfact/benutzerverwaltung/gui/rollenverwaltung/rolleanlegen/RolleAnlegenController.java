package de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.rolleanlegen;

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


import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.HinweisSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.RolleModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.awkwrapper.RollenverwaltungAwkWrapper;
import de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.common.controller.AbstractRollenverwaltungController;
import de.bund.bva.isyfact.common.web.global.MessageController;
import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.springframework.context.MessageSource;

/**
 * Controller zum Anlegen von Rollen.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class RolleAnlegenController extends AbstractRollenverwaltungController<RolleAnlegenModel> {

    private static final IsyLogger LOG = IsyLoggerFactory.getLogger(RolleAnlegenController.class);

    public RolleAnlegenController(MessageController messageController, MessageSource messageSource,
        RollenverwaltungAwkWrapper awkWrapper) {
        super(messageController, messageSource, awkWrapper);
    }

    @Override
    public void initialisiereModel(RolleAnlegenModel model) {
    }

    /**
     * Legt den Benutzer über den AWK-Wrapper an.
     */
    public void legeRolleAn(RolleAnlegenModel model) {
        try {
            RolleModel rolleModel = getAwkWrapper().legeRolleAn(model.getRolle());
            model.setRolle(new RolleModel());

            String nachricht = MessageSourceHolder
                .getMessage(HinweisSchluessel.ROLLE_ERSTELLT, rolleModel.getRollenId());
            getMessageController().writeSuccessMessage(nachricht);
            LOG.debugFachdaten(nachricht);
        } catch (BenutzerverwaltungValidationException validationException) {
            erzeugeNachrichten(validationException);
        }
    }

    @Override
    protected Class<RolleAnlegenModel> getMaskenModelKlasseZuController() {
        return RolleAnlegenModel.class;
    }

}
