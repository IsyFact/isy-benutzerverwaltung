package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.common.controller;

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

/**
 * Klasse zum Speichern von (Validierungs-)Nachrichten.
 *
 * @author Simon Spielmann, simon.spielmann@capgemini.com
 */
public class Nachricht {
    private Typ typ;

    private String text;

    public Nachricht(Typ typ, String text) {
        this.typ = typ;
        this.text = text;
    }

    public Typ getTyp() {
        return typ;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Nachricht [typ=" + typ + ", text=" + text + "]";
    }

    public enum Typ {
        FEHLER, WARNUNG, HINWEIS
    }

}
