package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzerselbstbearbeiten;

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

import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;
import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;

public class BenutzerSelbstBearbeitenModel extends AbstractMaskenModel {

    private static final long serialVersionUID = -987443602902568569L;

    private BenutzerModel benutzer;

    public BenutzerModel getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(BenutzerModel benutzer) {
        this.benutzer = benutzer;
    }


}
