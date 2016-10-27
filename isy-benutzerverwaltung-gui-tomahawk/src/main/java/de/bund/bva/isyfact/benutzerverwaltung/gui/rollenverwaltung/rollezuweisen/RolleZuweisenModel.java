package de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.rollezuweisen;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.model.SelectItem;

import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzersuchen.BenutzerSuchkriterienModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.RolleModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.AbstractSuchDataTableModel;

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
