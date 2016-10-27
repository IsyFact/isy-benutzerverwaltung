package de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.rollesuchen;

import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.FehlerSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.RolleSortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.controller.SucheController;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.konstanten.HinweisSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.RolleModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.SuchergebnisModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.TrefferlisteModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.awkwrapper.RollenverwaltungAwkWrapper;
import de.bund.bva.isyfact.common.web.global.MessageController;
import de.bund.bva.pliscommon.sicherheit.Sicherheit;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.webflow.execution.RequestContextHolder;

import java.util.List;
import java.util.Objects;

/**
 * Controller für die Suche nach Rollen.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class RolleSuchenController extends SucheController<RolleSuchenModel> {

    private final RollenverwaltungAwkWrapper awkWrapper;

    private final Sicherheit<?> sicherheit;

    public RolleSuchenController(MessageController messageController, MessageSource messageSource,
        RollenverwaltungAwkWrapper awkWrapper, Sicherheit<?> sicherheit) {
        super(messageController, messageSource);
        this.awkWrapper = awkWrapper;
        this.sicherheit = sicherheit;
    }

    /**
     * Diese Methode initialisiert das Filterkriterien Model. Es werden die Filter zurueckgesetzt und
     * DropDown-Menues mit Werten belegt.
     *
     * @param rolleSuchenModel das Model.
     */
    public void initialisiereModel(RolleSuchenModel rolleSuchenModel) {
        rolleSuchenModel.setTrefferliste(new RollenTrefferlisteModel());
        filterZuruecksetzen(rolleSuchenModel);
    }

    public void filterZuruecksetzen(RolleSuchenModel rolleSuchenModel) {
        rolleSuchenModel.getTrefferliste().setSuchkriterien(new RolleSuchkriterienModel());
    }

    /**
     * Löscht eine Rolle.
     *
     * @param model Model der Rolle.
     * @return {@code true}, wenn das Löschen erfolgreich war, ansonsten {@code false}.
     */
    public boolean loescheRolle(RolleModel model) {
        try {
            awkWrapper.loescheRolle(model.getRollenId());
            // Das Leeren des Caches zwingt alle Benutzer intern zur erneuten Anmeldung.
            // Der gelöschte Benutzer stößt auf einen Fehler und ist so nicht mehr angemeldet.
            sicherheit.leereCache();
            getMessageController().writeSuccessMessage(
                MessageSourceHolder.getMessage(HinweisSchluessel.ROLLE_GELOESCHT, model.getRollenId()));
            return true;
        } catch (DataIntegrityViolationException e) {
            // Dieser Fehler passiert während des Abschlusses der Transaktion.
            // Die Rolle kann nicht gelöscht werden, weil von irgendwoher auf sie verwiesen wird.
            erzeugeNachrichten(
                new BenutzerverwaltungBusinessException(FehlerSchluessel.ROLLE_LOESCHEN_NICHT_MOEGLICH,
                    model.getRollenId()));
            return false;
        } catch (BenutzerverwaltungBusinessException validationException) {
            erzeugeNachrichten(validationException);
            return false;
        }
    }

    @Override
    protected Class<RolleSuchenModel> getMaskenModelKlasseZuController() {
        return RolleSuchenModel.class;
    }

    /**
     * Model für die Trefferliste. Ist als innere Klasse des Controllers implementiert, da
     * Models eigentlich keinen direkten Zugang zum AWK-Wrapper haben dürfen.
     *
     * @author msg systems ag, Stefan Dellmuth
     */
    private static class RollenTrefferlisteModel
        extends TrefferlisteModel<RolleModel, RolleSuchkriterienModel> {

        @Override
        protected SuchergebnisModel<RolleModel> suche(RolleSuchkriterienModel suchkriterien,
            Sortierung sortierung, Paginierung paginierung) throws BenutzerverwaltungValidationException {
            RollenverwaltungAwkWrapper awkWrapper =
                RequestContextHolder.getRequestContext().getActiveFlow().getApplicationContext()
                    .getBean(RollenverwaltungAwkWrapper.class);
            return awkWrapper.sucheRollen(suchkriterien, sortierung, paginierung);
        }

        @Override
        protected Sortierattribut ermittleSortierattribut(String sortierAttribut) {
            try {
                return RolleSortierattribut.valueOf(sortierAttribut);
            } catch (IllegalArgumentException | NullPointerException e) {
                return RolleSortierattribut.getStandard();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public RolleModel getRowData(String rowKey) {
            for (RolleModel treffer : (List<RolleModel>) getWrappedData()) {
                if (Objects.equals(rowKey, getRowKey(treffer))) {
                    return treffer;
                }
            }
            return null;
        }

        @Override
        public Object getRowKey(RolleModel object) {
            return object.getRollenId();
        }
    }

}
