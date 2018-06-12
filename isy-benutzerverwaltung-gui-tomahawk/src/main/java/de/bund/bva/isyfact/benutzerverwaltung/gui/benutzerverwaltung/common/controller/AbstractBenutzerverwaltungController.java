package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.controller;

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

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation.PasswortKorrekt;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.BenutzerverwaltungAwkWrapper;
import de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.awkwrapper.RollenverwaltungAwkWrapper;
import de.bund.bva.isyfact.benutzerverwaltung.gui.selfservice.awkwrapper.SelfServiceAwkWrapper;
import de.bund.bva.isyfact.common.web.global.AbstractGuiController;
import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;
import de.bund.bva.isyfact.common.web.global.MessageController;
import de.bund.bva.isyfact.common.web.validation.ValidationController;
import de.bund.bva.isyfact.common.web.validation.ValidationMessage;
import org.springframework.beans.factory.annotation.Required;

/**
 * Diese Klasse bietet einen zentralen Zugriff für alle Controller auf den
 * AwkWrapper und implementiert Standard-Funktionen.
 *
 * @author Capgemini, Jonas Zitz
 * @version $Id: AbstractBenutzerverwaltungController.java 41870 2013-07-25
 * 13:54:34Z jozitz $
 */
public abstract class AbstractBenutzerverwaltungController<T extends AbstractMaskenModel>
    extends AbstractGuiController<T> {

    private BenutzerverwaltungAwkWrapper benutzerverwaltungAwkWrapper;

    private RollenverwaltungAwkWrapper rollenverwaltungAwkWrapper;

    private SelfServiceAwkWrapper selfServiceAwkWrapper;

    private MessageController messageController;

    private ValidationController validationController;

    protected BenutzerverwaltungAwkWrapper getBenutzerverwaltungAwkWrapper() {
        return benutzerverwaltungAwkWrapper;
    }

    @Required
    public void setBenutzerverwaltungAwkWrapper(BenutzerverwaltungAwkWrapper benutzerverwaltungAwkWrapper) {
        this.benutzerverwaltungAwkWrapper = benutzerverwaltungAwkWrapper;
    }

    public RollenverwaltungAwkWrapper getRollenverwaltungAwkWrapper() {
        return rollenverwaltungAwkWrapper;
    }

    @Required
    public void setRollenverwaltungAwkWrapper(RollenverwaltungAwkWrapper rollenverwaltungAwkWrapper) {
        this.rollenverwaltungAwkWrapper = rollenverwaltungAwkWrapper;
    }

    public SelfServiceAwkWrapper getSelfServiceAwkWrapper() {
        return selfServiceAwkWrapper;
    }

    @Required
    public void setSelfServiceAwkWrapper(SelfServiceAwkWrapper selfServiceAwkWrapper) {
        this.selfServiceAwkWrapper = selfServiceAwkWrapper;
    }

    protected MessageController getMessageController() {
        return messageController;
    }

    public void setMessageController(MessageController messageController) {
        this.messageController = messageController;
    }

    public ValidationController getValidationController() {
        return validationController;
    }

    public void setValidationController(ValidationController validationController) {
        this.validationController = validationController;
    }

    /**
     * Erzeugt Nachrichten aus Fehlern während der Validation des
     * Anwendungskerns.
     *
     * @param exception
     *     Ausnahme des Anwendungskerns
     */
    protected void zeigeNachricht(BenutzerverwaltungBusinessException exception) {
        if (exception instanceof BenutzerverwaltungValidationException) {
            BenutzerverwaltungValidationException validationException =
                (BenutzerverwaltungValidationException) exception;

            List<ValidationMessage> validationMessages =
                validationException.getFehler().stream().map(this::toValidationMessage)
                    .collect(Collectors.toList());

            getValidationController().processValidationMessages(validationMessages);
        } else {
            getMessageController().writeErrorMessage(exception.getFehlertext(), exception.getFehlertext());
        }
    }

    private ValidationMessage toValidationMessage(ConstraintViolation<?> violation) {
        String reference = violation.getPropertyPath().toString();

        // Sonderfälle für Validation-Annotationen, die an einer Klasse stehen,
        // da dort kein PropertyPath gesetzt wird
        if (reference.isEmpty()) {
            // Feld für altes Passwort bei Änderung durch Benutzer selbst
            if (violation.getConstraintDescriptor().getAnnotation().annotationType().equals(PasswortKorrekt.class)) {
                reference = "altesPasswort";
            } else if (violation.getRootBeanClass().getName().toLowerCase().contains("passwort")) { // Passwort-Felder
                reference = "passwort";
            }
        }

        return new ValidationMessage("VA", reference, reference, violation.getMessage());
    }

}
