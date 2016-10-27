package de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.common.controller;

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
