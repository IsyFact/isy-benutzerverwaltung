package de.bund.bva.isyfact.benutzerverwaltung.gui.common.controller;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Required;

import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierrichtung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.BenutzerverwaltungAwkWrapper;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.AbstractSuchDataTableModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.SuchergebnisModel;
import de.bund.bva.isyfact.common.web.global.MessageController;
import de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableController;
import de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableItem;
import de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableRequest;
import de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableResult;
import de.bund.bva.isyfact.common.web.jsf.components.datatable.SortDirection;
import de.bund.bva.isyfact.common.web.validation.ValidationController;
import de.bund.bva.isyfact.common.web.validation.ValidationMessage;

/**
 * Abstrakte Basisklasse für alle Controller, die Suchen mit einer plis-web
 * DataTable durchführen.
 *
 * @param <R>
 *            Der Typ des Items in der DataTable.
 * @param <K>
 *            Der Typ des Suchkriterien-Models.
 * @param <M>
 *            Der Typ des passenden DataTableModels.
 * @param <S>
 *            Der Typ des Sortierattribut passend zum Typ des Items in der
 *            DataTable.
 * 
 * @author msg systems ag, Björn Saxe
 */
public abstract class AbstractSuchDataTableController<R extends DataTableItem, K, M extends AbstractSuchDataTableModel<R, K>, S extends Enum<S> & Sortierattribut>
	extends DataTableController<R, M> {

    protected abstract SuchergebnisModel<R> sucheEntitaet(K kriterien, Sortierung sortierung, Paginierung paginierung)
	    throws BenutzerverwaltungValidationException;

    private BenutzerverwaltungAwkWrapper awkWrapper;
    private MessageController messageController;
    private ValidationController validationController;

    /**
     * @see de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableController#readItems(de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableModel,
     *      de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableRequest)
     */
    @Override
    protected DataTableResult<R> readItems(M model, DataTableRequest request) {
	DataTableResult<R> result = new DataTableResult<>();

	K kriterien = model.getSuchkriterien();
	Class<S> enumType = ((Class<S>) ((ParameterizedType) getClass().getGenericSuperclass())
		.getActualTypeArguments()[3]);
	S sortiereNach = Enum.valueOf(enumType, request.getSortProperty());
	Sortierrichtung richtung = request.getSortDirection().equals(SortDirection.ASCENDING)
		? Sortierrichtung.AUFSTEIGEND : Sortierrichtung.ABSTEIGEND;
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
     *            Das Suchmodel.
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

    public BenutzerverwaltungAwkWrapper getAwkWrapper() {
	return awkWrapper;
    }

    @Required
    public void setAwkWrapper(BenutzerverwaltungAwkWrapper awkWrapper) {
	this.awkWrapper = awkWrapper;
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
	String messageText = violation.getMessage();

	return new ValidationMessage("VA", reference, reference, messageText);
    }

    /**
     * Speichert das Item aus der DataTable, für das der zugehörige
     * Action-Button in einer Tabellenzeile gedrückt wurde, im Model. Diese
     * Methode ist ein Workaround, da  <code>&lt;isy:buttonIcon&gt;</code> keine
     *  <code>&lt;f:setPropertyActionListener&gt;</code> unterstützt. Beispielhafte Verwendung:
     * 
     * <code>
     * &lt;isy:buttonIcon action="#{controller.setzeAusgewaehltenTreffer(model, dataTableItem, 'someAction')}..."
     * </code>
     * 
     * @param model
     *            Das Model, in dem das Item gespeichert werden soll
     * @param item
     *            Das Item
     * @param action
     *            Bezeichner für die Action, anhand dessen nach dem Drücken des
     *            Button eine Transition im Flow ausgelöst werden soll
     * @return die als <code>action</code> übergebene Action.
     */
    public String setzeAusgewaehltenTreffer(M model, R item, String action) {
	model.setAusgewaehlterTreffer(item);

	return action;
    }
}
