package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.impl;

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
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSuchkriterien;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.Benutzerverwaltung;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.*;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.BenutzerDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.RollenDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Benutzer;
import org.dozer.Mapper;
import org.dozer.MappingException;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Standard-Implementierung des Benutzerverwaltung-Interfaces.
 *
 * @author Capgemini, Jonas Zitz
 * @version $Id: BenutzerverwaltungImpl.java 41878 2013-07-26 10:31:50Z jozitz $
 */
public class BenutzerverwaltungImpl implements Benutzerverwaltung {

    private final AwfBenutzerSuchen awfBenutzerSuchen;

    private final AwfBenutzerVerwalten awfBenutzerVerwalten;

    private final AwfBenutzerAnAbmelden awfBenutzerAnAbmelden;

    private final Mapper mapper;

    public BenutzerverwaltungImpl(BenutzerDao benutzerDao, RollenDao rollenDao,
        PasswordEncoder passwordEncoder, Mapper mapper, Validator validator) {
        awfBenutzerAnAbmelden = new AwfBenutzerAnAbmelden(benutzerDao, validator);
        awfBenutzerSuchen = new AwfBenutzerSuchen(benutzerDao, validator);
        awfBenutzerVerwalten = new AwfBenutzerVerwalten(benutzerDao, rollenDao, passwordEncoder, validator);
        this.mapper = mapper;
    }

    @Override
    public BenutzerDaten leseBenutzer(String benutzername) throws BenutzerverwaltungBusinessException {
        try {
            return mappeErgebnis(awfBenutzerSuchen.leseBenutzer(benutzername));
        } catch (RuntimeException e) {
            throw mappeRuntimeException(e);
        }
    }

    @Override
    public Suchergebnis<BenutzerDaten> sucheBenutzer(BenutzerSuchkriterien suchkriterien,
        Sortierung sortierung, Paginierung paginierung) throws BenutzerverwaltungValidationException {
        try {
            List<BenutzerDaten> trefferliste =
                awfBenutzerSuchen.sucheBenutzer(suchkriterien, sortierung, paginierung).stream()
                    .map(this::mappeErgebnis).collect(Collectors.toList());
            long anzahlTreffer = awfBenutzerSuchen.zaehleBenutzer(suchkriterien);
            return new Suchergebnis<>(trefferliste, anzahlTreffer);
        } catch (RuntimeException e) {
            throw mappeRuntimeException(e);
        }
    }

    @Override
    public BenutzerDaten legeBenutzerAn(BenutzerAnlegen benutzerAnlegen)
        throws BenutzerverwaltungValidationException {
        try {
            return mappeErgebnis(awfBenutzerVerwalten.legeBenutzerAn(benutzerAnlegen));
        } catch (RuntimeException e) {
            throw mappeRuntimeException(e);
        }
    }

    @Override
    public BenutzerDaten setzePasswort(PasswortAendern passwortAendern)
        throws BenutzerverwaltungBusinessException {
        try {
            return mappeErgebnis(awfBenutzerVerwalten.setzePasswort(passwortAendern));
        } catch (RuntimeException e) {
            throw mappeRuntimeException(e);
        }
    }

    @Override
    public BenutzerDaten setzePasswortZurueck(PasswortZuruecksetzen passwortZuruecksetzen)
        throws BenutzerverwaltungBusinessException {
        try {
            return mappeErgebnis(awfBenutzerVerwalten.setzePasswortZurueck(passwortZuruecksetzen));
        } catch (RuntimeException e) {
            throw mappeRuntimeException(e);
        }
    }

    @Override
    public BenutzerDaten setzeStatus(String benutzername, BenutzerStatus neuerStatus)
        throws BenutzerverwaltungBusinessException {
        try {
            return mappeErgebnis(awfBenutzerVerwalten.setzeStatus(benutzername, neuerStatus));
        } catch (RuntimeException e) {
            throw mappeRuntimeException(e);
        }
    }

    @Override
    public BenutzerDaten aendereBenutzer(BenutzerAendern benutzerAendern)
        throws BenutzerverwaltungBusinessException {
        try {
            return mappeErgebnis(awfBenutzerVerwalten.aendereBenutzer(benutzerAendern));
        } catch (RuntimeException e) {
            throw mappeRuntimeException(e);
        }
    }

    @Override
    public BenutzerDaten aendereBenutzerSelbst(BenutzerSelbstAendern benutzerSelbstAendern)
        throws BenutzerverwaltungBusinessException {
        try {
            return mappeErgebnis(awfBenutzerVerwalten.aendereBenutzerSelbst(benutzerSelbstAendern));
        } catch (RuntimeException e) {
            throw mappeRuntimeException(e);
        }
    }

    @Override
    public void loescheBenutzer(String benutzername) throws BenutzerverwaltungBusinessException {
        try {
            awfBenutzerVerwalten.loescheBenutzer(benutzername);
        } catch (RuntimeException e) {
            throw mappeRuntimeException(e);
        }
    }

    @Override
    public BenutzerDaten speichereErfolgreicheAnmeldung(String benutzername)
        throws BenutzerverwaltungBusinessException {
        try {
            return mappeErgebnis(awfBenutzerAnAbmelden.speichereErfolgreicheAnmeldung(benutzername));
        } catch (RuntimeException e) {
            throw mappeRuntimeException(e);
        }
    }

    @Override
    public BenutzerDaten speichereFehlgeschlageneAnmeldung(String benutzername)
        throws BenutzerverwaltungBusinessException {
        try {
            return mappeErgebnis(awfBenutzerAnAbmelden.speichereFehlgeschlageneAnmeldung(benutzername));
        } catch (RuntimeException e) {
            throw mappeRuntimeException(e);
        }
    }

    @Override
    public BenutzerDaten speichereAbmeldung(String benutzername) throws BenutzerverwaltungBusinessException {
        try {
            return mappeErgebnis(awfBenutzerAnAbmelden.speichereAbmeldung(benutzername));
        } catch (RuntimeException e) {
            throw mappeRuntimeException(e);
        }
    }

    @Override
    public void weiseRolleZu(RolleDaten rolle, List<String> benutzernamen)
        throws BenutzerverwaltungBusinessException {
        try {
            awfBenutzerVerwalten.weiseRolleZu(rolle, benutzernamen);
        } catch (RuntimeException e) {
            throw mappeRuntimeException(e);
        }
    }

    @Override
    public void entzieheRolle(RolleDaten rolle, List<String> benutzernamen)
        throws BenutzerverwaltungBusinessException {
        try {
            awfBenutzerVerwalten.entzieheRolle(rolle, benutzernamen);
        } catch (RuntimeException e) {
            throw mappeRuntimeException(e);
        }
    }

    /**
     * Konvertiert das Ergebnis eines Anwendungsfalls in ein Schnittstellen-Objekt.
     *
     * @param benutzer Persistenz-Objekt
     * @return das zugehörige Schnittstellen-Objekt.
     */
    private BenutzerDaten mappeErgebnis(Benutzer benutzer) {
        return mapper.map(benutzer, BenutzerDaten.class);
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
                FehlerSchluessel.MSG_TECHNISCHER_FEHLER_KONVERTIERUNG, Benutzer.class.getName(),
                BenutzerDaten.class.getName());
        } else if (e instanceof DataAccessException) {
            return new BenutzerverwaltungTechnicalRuntimeException(
                FehlerSchluessel.MSG_TECHNISCHER_FEHLER_DATENBANK, e);
        } else {
            return new BenutzerverwaltungTechnicalRuntimeException(
                FehlerSchluessel.ALLGEMEINER_TECHNISCHER_FEHLER, e);
        }

    }

}
