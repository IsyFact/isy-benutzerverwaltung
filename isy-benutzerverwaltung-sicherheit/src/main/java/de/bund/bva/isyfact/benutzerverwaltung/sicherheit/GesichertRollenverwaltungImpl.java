package de.bund.bva.isyfact.benutzerverwaltung.sicherheit;

/*-
 * #%L
 * IsyFact Benutzerverwaltung Sicherheit
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

import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Suchergebnis;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.RolleSuchkriterien;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.Rollenverwaltung;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.daten.RolleAendern;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.daten.RolleAnlegen;
import de.bund.bva.isyfact.benutzerverwaltung.sicherheit.konstanten.RollenverwaltungRecht;
import de.bund.bva.pliscommon.sicherheit.annotation.Gesichert;

/**
 * Implementierung der Rollenverwaltung, die alle Aufrufe mit den folgenden Rechten absichert:
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class GesichertRollenverwaltungImpl implements GesichertRollenverwaltung {

    private final Rollenverwaltung rollenverwaltung;

    public GesichertRollenverwaltungImpl(Rollenverwaltung rollenverwaltung) {
        this.rollenverwaltung = rollenverwaltung;
    }

    @Override
    public RolleDaten leseRolle(String rolleId) throws BenutzerverwaltungBusinessException {
        return rollenverwaltung.leseRolle(rolleId);
    }

    @Override
    @Gesichert(RollenverwaltungRecht.ROLLE_SUCHEN)
    public Suchergebnis<RolleDaten> sucheRollen(RolleSuchkriterien filter, Sortierung sortierung,
        Paginierung paginierung) throws BenutzerverwaltungValidationException {
        return rollenverwaltung.sucheRollen(filter, sortierung, paginierung);
    }

    @Override
    @Gesichert(RollenverwaltungRecht.ROLLE_ANLEGEN)
    public RolleDaten legeRolleAn(RolleAnlegen rolleAnlegen) throws BenutzerverwaltungValidationException {
        return rollenverwaltung.legeRolleAn(rolleAnlegen);
    }

    @Override
    @Gesichert(RollenverwaltungRecht.ROLLE_AENDERN)
    public RolleDaten aendereRolle(RolleAendern rolleAendern)
        throws BenutzerverwaltungValidationException {
        return rollenverwaltung.aendereRolle(rolleAendern);
    }

    @Override
    @Gesichert(RollenverwaltungRecht.ROLLE_LOESCHEN)
    public void loescheRolle(String rolleId) throws BenutzerverwaltungBusinessException {
        rollenverwaltung.loescheRolle(rolleId);
    }

}
