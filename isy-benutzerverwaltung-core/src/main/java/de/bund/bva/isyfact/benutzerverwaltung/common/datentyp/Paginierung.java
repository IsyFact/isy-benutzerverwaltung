package de.bund.bva.isyfact.benutzerverwaltung.common.datentyp;

/*-
 * #%L
 * IsyFact Benutzerverwaltung Core
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

import java.io.Serializable;

/**
 * DTO zur Angabe von Paginierungsinformationen zur Eingrenzung eines Suchergebnisses.
 *
 * @author Capgemini, Jonas Zitz
 */
public class Paginierung implements Serializable {

    /**
     * ID
     */
    private static final long serialVersionUID = 1L;

    private int ersterTreffer;

    private int trefferProSeite;

    /**
     * Konstruktur.
     *
     * @param ersterTreffer   ist der Treffer, bei der die Suchergebnisse starten sollen (erster Treffer = 0)
     * @param trefferProSeite ist die Anzahl an Treffern, die geliefert werden soll.
     */
    public Paginierung(int ersterTreffer, int trefferProSeite) {
        this.ersterTreffer = ersterTreffer;
        this.trefferProSeite = trefferProSeite;
    }

    public int getErsterTreffer() {
        return ersterTreffer;
    }

    /**
     * @param ersterTreffer ist der Treffer, bei der die Suchergebnisse starten sollen (erster Treffer = 0)
     */
    public void setErsterTreffer(int ersterTreffer) {
        this.ersterTreffer = ersterTreffer;
    }

    public int getTrefferProSeite() {
        return trefferProSeite;
    }

    public void setTrefferProSeite(int trefferProSeite) {
        this.trefferProSeite = trefferProSeite;
    }

}
