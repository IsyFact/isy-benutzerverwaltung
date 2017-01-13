package de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.rollezuweisen;

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

import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzersuchen.BenutzerSuchkriterienModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.RolleModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.AbstractSuchDataTableModel;

import javax.faces.model.SelectItem;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Dieses Model stellt die Daten zur Rollenzuweisung bereit.
 *
 * @author msg systems ag, Björn Saxe
 */
public class RolleZuweisenModel extends AbstractSuchDataTableModel<BenutzerModel, BenutzerSuchkriterienModel> {

    private static final long serialVersionUID = 0L;

    private List<SelectItem> verfuegbareRollen;

    private List<String> verfuegbareRollenIds;

    private List<RolleModel> alleRollen;

    private String rollenIdZurZuweisung;

    /*
     * Enthält Zuordnung von Benutzern (als Long-ID) zu Rollen (Rollen-ID und
     * Boolean). Notwendig für das data binding in der DataTable. Bsp.:
     * 
     * 1234 -> Administrator -> False
     *         Benutzer -> True
     *         ...
     * 5678 -> Administrator -> True
     *         Benutzer -> True
     *         ...
     * ...
     */
    private Map<Long, Map<String, Boolean>> rollenZuordnung;

    private Set<String> ausgewaehlteBenutzernamen = new HashSet<>();

    public List<SelectItem> getVerfuegbareRollen() {
	return verfuegbareRollen;
    }

    public void setVerfuegbareRollen(List<SelectItem> verfuegbareRollen) {
	this.verfuegbareRollen = verfuegbareRollen;
    }

    public List<String> getVerfuegbareRollenIds() {
	return verfuegbareRollenIds;
    }

    public void setVerfuegbareRollenIds(List<String> verfuegbareRollenIds) {
	this.verfuegbareRollenIds = verfuegbareRollenIds;
    }

    /**
     * @return the rollenZuordnung
     */
    public Map<Long, Map<String, Boolean>> getRollenZuordnung() {
	return rollenZuordnung;
    }

    /**
     * @param rollenZuordnung
     *            the rollenZuordnung to set
     */
    public void setRollenZuordnung(Map<Long, Map<String, Boolean>> rollenZuordnung) {
	this.rollenZuordnung = rollenZuordnung;
    }

    /**
     * @return the rollenIdZurZuweisung
     */
    public String getRollenIdZurZuweisung() {
	return rollenIdZurZuweisung;
    }

    /**
     * @param rollenIdZurZuweisung
     *            the rollenIdZurZuweisung to set
     */
    public void setRollenIdZurZuweisung(String rollenIdZurZuweisung) {
	this.rollenIdZurZuweisung = rollenIdZurZuweisung;
    }

    public List<RolleModel> getAlleRollen() {
	return alleRollen;
    }

    public void setAlleRollen(List<RolleModel> alleRollen) {
	this.alleRollen = alleRollen;
    }

    /**
     * @return the ausgewaehlteBenutzernamen
     */
    public Set<String> getAusgewaehlteBenutzernamen() {
	return ausgewaehlteBenutzernamen;
    }
}
