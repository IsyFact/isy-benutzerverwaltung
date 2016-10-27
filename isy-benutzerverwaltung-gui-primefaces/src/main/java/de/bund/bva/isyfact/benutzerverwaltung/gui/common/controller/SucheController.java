package de.bund.bva.isyfact.benutzerverwaltung.gui.common.controller;

import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.SucheModel;
import de.bund.bva.isyfact.common.web.global.MessageController;
import org.springframework.context.MessageSource;

/**
 * Controller für einen Flow, der eine Suche implementiert.
 *
 * @param <T> Typ des Flow-Models
 * @author msg systems ag, Stefan Dellmuth
 */
public abstract class SucheController<T extends SucheModel> extends AbstractGuiController<T> {

    protected SucheController(MessageController messageController, MessageSource messageSource) {
        super(messageController, messageSource);
    }

    /**
     * Setzt den Filter (d.h. die Suchkriterien) auf ihren Ausgangszustand zurück.
     *
     * @param sucheModel Model der Suche
     */
    public abstract void filterZuruecksetzen(T sucheModel);

    /**
     * Zeigt Fehler, die bei der letzten Suche aufgetreten sind, als Fehlermeldungen an.
     *
     * @param sucheModel Model der Suche
     */
    public void zeigeSuchfehlerAn(T sucheModel) {
        if (sucheModel.getTrefferliste().getException() != null) {
            erzeugeNachrichten(sucheModel.getTrefferliste().getException());
        }
    }

}
