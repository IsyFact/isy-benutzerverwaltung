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
