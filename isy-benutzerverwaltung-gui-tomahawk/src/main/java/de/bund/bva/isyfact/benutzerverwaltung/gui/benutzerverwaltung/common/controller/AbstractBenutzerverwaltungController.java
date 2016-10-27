package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.BenutzerverwaltungAwkWrapper;
import de.bund.bva.isyfact.common.web.global.AbstractGuiController;
import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;
import de.bund.bva.isyfact.common.web.global.MessageController;
import de.bund.bva.isyfact.common.web.validation.ValidationController;
import de.bund.bva.isyfact.common.web.validation.ValidationMessage;

/**
 * Diese Klasse bietet einen zentralen Zugriff für alle Controller auf den
 * AwkWrapper und implementiert Standard-Funktionen.
 *
 * @author Capgemini, Jonas Zitz
 * @version $Id: AbstractBenutzerverwaltungController.java 41870 2013-07-25
 *          13:54:34Z jozitz $
 */
public abstract class AbstractBenutzerverwaltungController<T extends AbstractMaskenModel>
	extends AbstractGuiController<T> {

    private BenutzerverwaltungAwkWrapper awkWrapper;

    private MessageController messageController;

    private ValidationController validationController;

    protected BenutzerverwaltungAwkWrapper getAwkWrapper() {
	return awkWrapper;
    }

    public void setAwkWrapper(BenutzerverwaltungAwkWrapper awkWrapper) {
	this.awkWrapper = awkWrapper;
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
     *            Ausnahme des Anwendungskerns
     */
    protected void zeigeNachricht(BenutzerverwaltungBusinessException exception) {
	if (exception instanceof BenutzerverwaltungValidationException) {
	    BenutzerverwaltungValidationException validationException = (BenutzerverwaltungValidationException) exception;

	    List<ValidationMessage> validationMessages = validationException.getFehler().stream()
		    									.map(this::toValidationMessage)
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
	    String rootBeanClassName = violation.getRootBeanClass().getName().toLowerCase();
	    // Passwort
	    if (rootBeanClassName.contains("passwort"))
	    {
		reference = "passwort";
	    }
	}

	String messageText = violation.getMessage();

	return new ValidationMessage("VA", reference, reference, messageText);
    }

}