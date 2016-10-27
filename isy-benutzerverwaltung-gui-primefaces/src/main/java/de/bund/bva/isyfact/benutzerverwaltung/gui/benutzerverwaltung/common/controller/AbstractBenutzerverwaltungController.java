package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.controller;

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
 * @version $Id: AbstractBenutzerverwaltungController.java 41870 2013-07-25 13:54:34Z jozitz $
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
