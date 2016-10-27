package de.bund.bva.isyfact.benutzerverwaltung.gui.common.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 * Erlaubt den Zugriff auf die Faces-Nachrichten.
 *
 * @author Capgemini, Andreas Hörning
 */
public class FacesNachrichtenUtil {

    /**
     * Der Schlüssel für allgemeine Nachricht
     */
    private final static String GRUPPE_ALLGEMEIN = "allgemein";

    /**
     * Fügt eine Nachricht in den aktuellen {@link FacesContext} hinzu
     *
     * @param nachricht welche hinzugefügt werden soll
     * @param level     {@link Severity} der Nachricht
     */
    public final static void fuegeAllgemeineNachrichtHinzu(String nachricht, FacesMessage.Severity level) {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setDetail(nachricht);
        facesMessage.setSummary(nachricht);
        facesMessage.setSeverity(level);

        facesContext.addMessage(GRUPPE_ALLGEMEIN, facesMessage);

    }

}
