package de.bund.bva.isyfact.benutzerverwaltung.gui.common.controller;

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


import com.google.common.base.Joiner;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;
import de.bund.bva.isyfact.common.web.global.MessageController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.Locale;

/**
 * Diese Klasse bietet einen zentralen Zugriff für alle Controller auf den AwkWrapper und implementiert
 * Standard-Funktionen.
 *
 * @author Capgemini, Jonas Zitz
 * @version $Id: AbstractSelfServiceController.java 41870 2013-07-25 13:54:34Z jozitz $
 */
public abstract class AbstractGuiController<T extends AbstractMaskenModel>
    extends de.bund.bva.isyfact.common.web.global.AbstractGuiController<T> {
    /**
     * Ist ein Praefix fuer Validierungs-Messages.
     */
    private static final String MESSAGE_PREFIX = "MEL";

    private final MessageController messageController;

    private final MessageSource messageSource;

    protected AbstractGuiController(MessageController messageController, MessageSource messageSource) {
        this.messageController = messageController;
        this.messageSource = messageSource;
    }

    protected MessageController getMessageController() {
        return messageController;
    }

    protected void erzeugeNachrichten(BenutzerverwaltungBusinessException exception) {
        if (exception instanceof BenutzerverwaltungValidationException) {
            BenutzerverwaltungValidationException validationException =
                (BenutzerverwaltungValidationException) exception;
            for (ConstraintViolation<?> fehler : validationException.getFehler()) {
                writeViolationMessage(exception.getMessage(), fehler);
            }
        } else {
            messageController.writeWarnMessage(exception.getFehlertext(), exception.getAusnahmeId());
        }
    }

    private void writeViolationMessage(String message, ConstraintViolation<?> violation) {
        String attributName = getPropertyName(violation);
        String propertyDescription = getPropertyDescription(attributName, violation);
        String violationMessage = violation.getMessage();
        if (violationMessage.startsWith(":")) {
            messageController.writeWarnMessage(propertyDescription + violationMessage, message);
        } else {
            messageController.writeWarnMessage(propertyDescription + " " + violationMessage, message);
        }
    }

    /**
     * Gibt die Nachricht für die Property aus.
     *
     * @param attributName Name des Attributs
     * @param violation    Validierungsfehler
     * @return die Beschreibung des Validierungsfehlers.
     */
    private String getPropertyDescription(String attributName, ConstraintViolation<?> violation) {
        if (violation.getPropertyPath() == null || "".equals(violation.getPropertyPath().toString())) {
            return "";
        }

        String classNameLowerCase = violation.getLeafBean().getClass().getSimpleName().toLowerCase();

        // In einer MessageSource ist die Property "MEL_<Klassenname>_<Attributname>" vorhanden
        String descriptionKey =
            Joiner.on("_").skipNulls().join(MESSAGE_PREFIX, classNameLowerCase, attributName);

        try {
            return messageSource.getMessage(descriptionKey, new Object[0], Locale.GERMAN);
        } catch (NoSuchMessageException e) {
            if (attributName != null) {
                return "Feld \"" + attributName + "\"";
            } else {
                return "Nachricht mit Schlüssel \"" + descriptionKey + "\" nicht gefunden.";
            }
        }
    }

    /**
     * Gibt den Namen der Property zurück, welche die Violation verursacht hat.
     *
     * @param violation Die Violation
     * @return Der Name der Property.
     */
    private String getPropertyName(ConstraintViolation<?> violation) {
        Path.Node leafNode = null;
        for (Path.Node node : violation.getPropertyPath()) {
            leafNode = node;
        }
        if (leafNode == null) {
            return null;
        } else {
            return StringUtils.capitalize(leafNode.getName());
        }
    }

}
