package de.bund.bva.isyfact.benutzerverwaltung.gui.common.controller;

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

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;

import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierrichtung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.BenutzerverwaltungAwkWrapper;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.AbstractSuchDataTableModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.SuchergebnisModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.awkwrapper.RollenverwaltungAwkWrapper;
import de.bund.bva.isyfact.common.web.global.MessageController;
import de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableController;
import de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableItem;
import de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableRequest;
import de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableResult;
import de.bund.bva.isyfact.common.web.jsf.components.datatable.SortDirection;
import de.bund.bva.isyfact.common.web.validation.ValidationController;
import de.bund.bva.isyfact.common.web.validation.ValidationMessage;
import org.springframework.beans.factory.annotation.Required;

/**
 * Abstrakte Basisklasse für alle Controller, die Suchen mit einer plis-web
 * DataTable durchführen.
 *
 * @param <R>
 *     Der Typ des Items in der DataTable.
 * @param <K>
 *     Der Typ des Suchkriterien-Models.
 * @param <M>
 *     Der Typ des passenden DataTableModels.
 * @param <S>
 *     Der Typ des Sortierattribut passend zum Typ des Items in der
 *     DataTable.
 * @author msg systems ag, Björn Saxe
 */
public abstract class AbstractSuchDataTableController<R extends DataTableItem, K, M extends AbstractSuchDataTableModel<R, K>, S extends Enum<S> & Sortierattribut>
    extends DataTableController<R, M> {

    private BenutzerverwaltungAwkWrapper benutzerverwaltungAwkWrapper;

    private RollenverwaltungAwkWrapper rollenverwaltungAwkWrapper;

    private MessageController messageController;

    private ValidationController validationController;

    protected abstract SuchergebnisModel<R> sucheEntitaet(K kriterien, Sortierung sortierung,
        Paginierung paginierung) throws BenutzerverwaltungValidationException;

    /**
     * @see de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableController#readItems(de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableModel, * de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableRequest)
     */
    @Override
    protected DataTableResult<R> readItems(M model, DataTableRequest request) {
        DataTableResult<R> result = new DataTableResult<>();

        K kriterien = model.getSuchkriterien();
        Class<S> enumType =
            ((Class<S>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[3]);
        S sortiereNach = Enum.valueOf(enumType, request.getSortProperty());
        Sortierrichtung richtung = request.getSortDirection().equals(SortDirection.ASCENDING) ?
            Sortierrichtung.AUFSTEIGEND :
            Sortierrichtung.ABSTEIGEND;
        int erstesItem = request.getItemFrom();
        int itemsProSeite = request.getItemsCount();

        SuchergebnisModel<R> ergebnis = null;
        try {
            ergebnis = sucheEntitaet(kriterien, new Sortierung(sortiereNach, richtung),
                new Paginierung(erstesItem, itemsProSeite));
        } catch (BenutzerverwaltungValidationException exception) {
            getMessageController().writeException(exception);
        }

        result.setItems(ergebnis.getTrefferliste());
        result.setItemCount((int) ergebnis.getAnzahlTreffer());

        return result;
    }

    /**
     * @see de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableController#updateDisplayItems(de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableModel)
     */
    @Override
    public void updateDisplayItems(M model) {
        DataTableRequest request = new DataTableRequest();
        request.setSortDirection(model.getSortModel().getDirection());
        request.setSortProperty(model.getSortModel().getProperty());
        request.setItemFrom(getFirstDisplayItem(model.getPaginationModel()));
        request.setItemsCount(getCountDisplayItems(model.getPaginationModel()));

        DataTableResult<R> result = readItems(model, request);

        model.getDataModel().setDisplayItems(result.getItems());
        model.getDataModel().setFilteredItemCount(result.getItemCount());

        int pageSize = model.getPaginationModel().getPageSize();
        model.getPaginationModel()
            .setPageCount((model.getDataModel().getFilteredItemCount() + pageSize - 1) / pageSize);
    }

    /**
     * Führt die Suche durch, setzt den Paginator auf die erste Seite und setzt
     * die Elementauswahl zurück. Dazu wird <code>updateDisplayItems()</code>
     * aufgerufen.
     *
     * @param model
     *     Das Suchmodel.
     */
    public void suche(M model) {
        model.getPaginationModel().setCurrentPage(1);
        model.getSelectionModel().getSelectedItems().clear();
        updateDisplayItems(model);
    }

    public MessageController getMessageController() {
        return messageController;
    }

    @Required
    public void setMessageController(MessageController messageController) {
        this.messageController = messageController;
    }

    public BenutzerverwaltungAwkWrapper getBenutzerverwaltungAwkWrapper() {
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

    public ValidationController getValidationController() {
        return validationController;
    }

    @Required
    public void setValidationController(ValidationController validationController) {
        this.validationController = validationController;
    }

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

        return new ValidationMessage("VA", reference, reference, violation.getMessage());
    }

    /**
     * Speichert das Item aus der DataTable, für das der zugehörige
     * Action-Button in einer Tabellenzeile gedrückt wurde, im Model. Diese
     * Methode ist ein Workaround, da  <code>&lt;isy:buttonIcon&gt;</code> keine
     * <code>&lt;f:setPropertyActionListener&gt;</code> unterstützt. Beispielhafte Verwendung:
     *
     * <code>
     * &lt;isy:buttonIcon action="#{controller.setzeAusgewaehltenTreffer(model, dataTableItem, 'someAction')}..."
     * </code>
     *
     * @param model
     *     Das Model, in dem das Item gespeichert werden soll
     * @param item
     *     Das Item
     * @param action
     *     Bezeichner für die Action, anhand dessen nach dem Drücken des
     *     Button eine Transition im Flow ausgelöst werden soll
     * @return die als <code>action</code> übergebene Action.
     */
    public String setzeAusgewaehltenTreffer(M model, R item, String action) {
        model.setAusgewaehlterTreffer(item);

        return action;
    }
}
