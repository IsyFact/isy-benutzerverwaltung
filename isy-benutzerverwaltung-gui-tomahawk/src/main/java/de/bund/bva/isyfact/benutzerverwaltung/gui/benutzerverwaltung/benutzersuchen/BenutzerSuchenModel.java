package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzersuchen;

import java.util.List;

import javax.faces.model.SelectItem;

import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.AbstractSuchDataTableModel;

/**
 * @author msg systems ag, Björn Saxe
 *
 */
public class BenutzerSuchenModel extends AbstractSuchDataTableModel<BenutzerModel, BenutzerSuchkriterienModel> {

    private static final long serialVersionUID = 0L;

    private List<SelectItem> verfuegbareRollen;

    private List<SelectItem> verfuegbareBenutzerStatus;

    public List<SelectItem> getVerfuegbareRollen() {
        return verfuegbareRollen;
    }

    public void setVerfuegbareRollen(List<SelectItem> verfuegbareRollen) {
        this.verfuegbareRollen = verfuegbareRollen;
    }

    public List<SelectItem> getVerfuegbareBenutzerStatus() {
        return verfuegbareBenutzerStatus;
    }

    public void setVerfuegbareBenutzerStatus(List<SelectItem> verfuegbareBenutzerStatus) {
        this.verfuegbareBenutzerStatus = verfuegbareBenutzerStatus;
    }    
}
