package de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.impl;

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


import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Suchergebnis;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungTechnicalRuntimeException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.FehlerSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.RolleSuchkriterien;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.Rollenverwaltung;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.daten.RolleAendern;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.daten.RolleAnlegen;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.RollenDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Rolle;

import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.MappingException;
import org.springframework.dao.DataAccessException;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

/**
 * Standard-Implementierung der Schnittstelle {@link Rollenverwaltung}.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class RollenverwaltungImpl implements Rollenverwaltung {

    private final AwfRollenSuchen awfRollenSuchen;

    private final AwfRollenVerwalten awfRollenVerwalten;

    private final Mapper mapper;

    public RollenverwaltungImpl(RollenDao rollenDao, Mapper mapper, Validator validator) {
        awfRollenSuchen = new AwfRollenSuchen(rollenDao, validator);
        awfRollenVerwalten = new AwfRollenVerwalten(rollenDao, validator);
        this.mapper = mapper;
    }

    @Override
    public RolleDaten leseRolle(String rolleId) throws BenutzerverwaltungBusinessException {
        try {
            return mappeErgebnis(awfRollenSuchen.leseRolle(rolleId));
        } catch (RuntimeException e) {
            throw mappeRuntimeException(e);
        }
    }

    @Override
    public Suchergebnis<RolleDaten> sucheRollen(RolleSuchkriterien filter, Sortierung sortierung,
        Paginierung paginierung) throws BenutzerverwaltungValidationException {
        try {
            List<RolleDaten> trefferliste = new ArrayList<>();
            for (Rolle treffer : awfRollenSuchen.sucheRollen(filter, sortierung, paginierung)) {
                trefferliste.add(mappeErgebnis(treffer));
            }
            long anzahlTreffer = awfRollenSuchen.zaehleRollen(filter);
            return new Suchergebnis<>(trefferliste, anzahlTreffer);
        } catch (RuntimeException e) {
            throw mappeRuntimeException(e);
        }
    }

    @Override
    public RolleDaten legeRolleAn(RolleAnlegen rolleAnlegen) throws BenutzerverwaltungValidationException {
        try {
            return mappeErgebnis(awfRollenVerwalten.legeRolleAn(rolleAnlegen));
        } catch (RuntimeException e) {
            throw mappeRuntimeException(e);
        }
    }

    @Override
    public RolleDaten aendereRolle(RolleAendern rolleAendern)
        throws BenutzerverwaltungValidationException {
        try {
            return mappeErgebnis(awfRollenVerwalten.aendereRolle(rolleAendern));
        } catch (RuntimeException e) {
            throw mappeRuntimeException(e);
        }
    }

    @Override
    public void loescheRolle(String rolleId) throws BenutzerverwaltungBusinessException {
        try {
            awfRollenVerwalten.loescheRolle(rolleId);
        } catch (RuntimeException e) {
            throw mappeRuntimeException(e);
        }
    }

    /**
     * Konvertiert das Ergebnis eines Anwendungsfalls in ein Schnittstellen-Objekt.
     *
     * @param rolle Persistenz-Objekt
     * @return das zugehörige Schnittstellen-Objekt.
     */
    private RolleDaten mappeErgebnis(Rolle rolle) {
        return mapper.map(rolle, RolleDaten.class);
    }

    /**
     * Überprüft eine während eines Anwendungsfalls aufgetretene {@link RuntimeException} und konvertiert sie
     * in eine technische Schnittstellen-Exception.
     *
     * @param e aufgetretene {@link RuntimeException}
     * @return technische Schnittstellen-Exception, welche die {@link RuntimeException} als {@link
     * Exception#cause} enthält.
     */
    private BenutzerverwaltungTechnicalRuntimeException mappeRuntimeException(RuntimeException e) {
        if (e instanceof MappingException) {
            return new BenutzerverwaltungTechnicalRuntimeException(
                FehlerSchluessel.MSG_TECHNISCHER_FEHLER_KONVERTIERUNG, Rolle.class.getName(),
                RolleDaten.class.getName());
        } else if (e instanceof DataAccessException) {
            return new BenutzerverwaltungTechnicalRuntimeException(
                FehlerSchluessel.MSG_TECHNISCHER_FEHLER_DATENBANK, e);
        } else {
            return new BenutzerverwaltungTechnicalRuntimeException(
                FehlerSchluessel.ALLGEMEINER_TECHNISCHER_FEHLER, e);
        }

    }

}
