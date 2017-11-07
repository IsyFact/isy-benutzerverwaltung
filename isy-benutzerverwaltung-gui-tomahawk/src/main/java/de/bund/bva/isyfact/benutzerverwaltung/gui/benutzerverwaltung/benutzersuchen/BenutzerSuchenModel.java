package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzersuchen;

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

import java.util.List;
import javax.faces.model.SelectItem;

import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.AbstractSuchDataTableModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;

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
