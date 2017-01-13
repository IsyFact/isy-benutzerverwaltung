package de.bund.bva.isyfact.benutzerverwaltung.gui.common.util;

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
