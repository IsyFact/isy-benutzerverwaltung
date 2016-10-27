package de.bund.bva.isyfact.benutzerverwaltung.gui.common.util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.RolleModel;

/**
 * Diese Hilfsklasse bietet Hilfsfunktionen zur Benutzerverwaltung
 *
 * @author Capgemini, Jonas Zitz
 * @version $Revision: 41870 $
 */
public class AuswahlHelper {
    /**
     * Diese Funktion erstellt eine {@link List} von {@link SelectItem SelectItems} aller verfuegbaren
     * {@link BenutzerStatus} zur Nutzung in einer DropDown-Liste.
     *
     * @return eine {@link List} von {@link SelectItem SelectItems}
     */
    public static List<SelectItem> erstelleBenutzerStatusDropDown() {
        List<SelectItem> benutzerStatusItemList = new ArrayList<SelectItem>();

        for (BenutzerStatus b : BenutzerStatus.values()) {
            benutzerStatusItemList.add(new SelectItem(b, b.getBezeichnung()));
        }
        return benutzerStatusItemList;
    }

    /**
     * Diese Funktion erstellt eine {@link List} von {@link SelectItem SelectItems} aller verfuegbaren
     * {@link RolleModel Rollen} zur Nutzung in einer DropDown-Liste.
     *
     * @return eine {@link List} von {@link SelectItem SelectItems}
     */
    public static List<SelectItem> erstelleRollenDropDown(List<RolleModel> rollen) {
        List<SelectItem> rollenItemList = new ArrayList<SelectItem>();

        for (RolleModel r : rollen) {
            rollenItemList.add(new SelectItem(r.getRollenId(), r.getRollenName()));
        }
        return rollenItemList;
    }

    /**
     * Diese Funktion erstellt eine {@link List} von {@link SelectItem SelectItems} aller verfuegbaren
     * {@link BenutzerModel Benutzer} zur Nutzung in einer DropDown-Liste.
     *
     * @return eine {@link List} von {@link SelectItem SelectItems}
     */
    public static List<SelectItem> erstelleBenutzerSelectItems(List<BenutzerModel> benutzer) {
        List<SelectItem> benutzerItemList = new ArrayList<SelectItem>();

        for (BenutzerModel b : benutzer) {
            String anzeigeText = b.getBenutzername();
            benutzerItemList.add(new SelectItem(b.getId(), anzeigeText, anzeigeText));
        }
        return benutzerItemList;
    }

}
