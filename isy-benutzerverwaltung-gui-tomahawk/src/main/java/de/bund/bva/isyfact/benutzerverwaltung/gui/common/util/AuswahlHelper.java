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

import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.RolleModel;

import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

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
