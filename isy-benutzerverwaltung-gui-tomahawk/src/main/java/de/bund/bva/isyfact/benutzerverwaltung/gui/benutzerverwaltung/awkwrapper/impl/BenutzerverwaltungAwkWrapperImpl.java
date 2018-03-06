package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.impl;

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
import java.util.Objects;
import java.util.stream.Collectors;

import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierrichtung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Suchergebnis;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSuchkriterien;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.Benutzerverwaltung;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.BenutzerAendern;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.BenutzerAnlegen;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.BenutzerSelbstAendern;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.PasswortAendern;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.PasswortZuruecksetzen;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.Rollenverwaltung;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.BenutzerverwaltungAwkWrapper;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.daten.BenutzerAnlegenDaten;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.daten.BenutzerBearbeitenSelbst;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzerbearbeiten.BenutzerBearbeitenModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzersuchen.BenutzerSuchkriterienModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.RolleModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.SuchergebnisModel;
import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import org.dozer.Mapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Standard-Implementierung des Benutzerverwaltung-AWK-Wrapper Interfaces.
 *
 * @author Capgemini, Jonas Zitz
 * @version $Id: BenutzerverwaltungAwkWrapperImpl.java 41878 2013-07-26 10:31:50Z jozitz $
 */
@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
public class BenutzerverwaltungAwkWrapperImpl implements BenutzerverwaltungAwkWrapper {
    private static final IsyLogger LOG = IsyLoggerFactory.getLogger(BenutzerverwaltungAwkWrapperImpl.class);

    /**
     * Zugriff auf die Core-Schicht.
     */
    private final Benutzerverwaltung benutzerverwaltung;

    private final Rollenverwaltung rollenverwaltung;

    /**
     * Zugriff auf Dozer.
     */
    private final Mapper mapper;

    public BenutzerverwaltungAwkWrapperImpl(Benutzerverwaltung benutzerverwaltung,
        Rollenverwaltung rollenverwaltung, Mapper mapper) {
        this.benutzerverwaltung = benutzerverwaltung;
        this.rollenverwaltung = rollenverwaltung;
        this.mapper = mapper;
    }

    @Override
    public BenutzerModel leseBenutzer(String benutzername) throws BenutzerverwaltungBusinessException {
        return mapper.map(benutzerverwaltung.leseBenutzer(benutzername), BenutzerModel.class);
    }

    @Override
    public SuchergebnisModel<BenutzerModel> sucheBenutzer(BenutzerSuchkriterienModel filter,
        Sortierung sortierung, Paginierung paginierung) throws BenutzerverwaltungValidationException {
        BenutzerSuchkriterien coreSuchkriterien = mapper.map(filter, BenutzerSuchkriterien.class);

        // Sortierung mappen
        Sortierung coreSortierung =
            new Sortierung(BenutzerSortierattribut.getStandard(), Sortierrichtung.getStandard());
        if (sortierung != null) {
            coreSortierung.setAttribut(sortierung.getAttribut());
            coreSortierung.setRichtung(sortierung.getRichtung());
        }

        Suchergebnis<BenutzerDaten> suchergebnis =
            benutzerverwaltung.sucheBenutzer(coreSuchkriterien, coreSortierung, paginierung);
        SuchergebnisModel<BenutzerModel> suchergebnisModel = new SuchergebnisModel<>();
        suchergebnis.getTrefferliste().forEach(
            treffer -> suchergebnisModel.getTrefferliste().add(mapper.map(treffer, BenutzerModel.class)));
        suchergebnisModel.setAnzahlTreffer(suchergebnis.getAnzahlTreffer());

        return suchergebnisModel;
    }

    @Override
    public BenutzerModel legeBenutzerAn(BenutzerAnlegenDaten benutzerAnlegenDaten)
        throws BenutzerverwaltungBusinessException {
        BenutzerAnlegen benutzerAnlegen = mapper.map(benutzerAnlegenDaten, BenutzerAnlegen.class);
        for (String rollenId : benutzerAnlegenDaten.getRollenIds()) {
            benutzerAnlegen.getRollen().add(rollenverwaltung.leseRolle(rollenId));
        }
        return mapper.map(benutzerverwaltung.legeBenutzerAn(benutzerAnlegen), BenutzerModel.class);
    }

    @Override
    public BenutzerModel speichereAbmeldung(String benutzername) throws BenutzerverwaltungBusinessException {
        return mapper.map(benutzerverwaltung.speichereAbmeldung(benutzername), BenutzerModel.class);
    }



    @Override
    public void loescheBenutzer(BenutzerModel benutzer) throws BenutzerverwaltungBusinessException {
        benutzerverwaltung.loescheBenutzer(benutzer.getBenutzername());
    }

    @Override
    public void weiseRolleZu(RolleModel rolle, List<String> benutzernamen)
        throws BenutzerverwaltungBusinessException {
        benutzerverwaltung.weiseRolleZu(mapper.map(rolle, RolleDaten.class), benutzernamen);
    }

    @Override
    public void entzieheRolle(RolleModel rolle, List<String> benutzernamen)
        throws BenutzerverwaltungBusinessException {
        benutzerverwaltung.entzieheRolle(mapper.map(rolle, RolleDaten.class), benutzernamen);

    }

    @Override
    public void setzePasswortZurueck(String benutzername, String neuesPasswort,
        String neuesPasswortWiederholung) throws BenutzerverwaltungBusinessException {
        benutzerverwaltung.setzePasswortZurueck(
            new PasswortZuruecksetzen(benutzername, neuesPasswort, neuesPasswortWiederholung));
    }

    @Override
    public void setzePasswort(String benutzername, String altesPasswort, String neuesPasswort,
        String neuesPasswortWiederholung) throws BenutzerverwaltungBusinessException {
        benutzerverwaltung.setzePasswort(
            new PasswortAendern(benutzername, altesPasswort, neuesPasswort, neuesPasswortWiederholung));
    }

    @Override
    public BenutzerModel aendereBenutzerSelbst(BenutzerBearbeitenSelbst benutzer)
        throws BenutzerverwaltungBusinessException {
        BenutzerSelbstAendern benutzerSelbstAendern =
            new BenutzerSelbstAendern(benutzer.getBenutzername(), benutzer.getNachname(),
                benutzer.getVorname(), benutzer.getBehoerde(), benutzer.getEmailAdresse(),
                benutzer.getTelefonnummer());
        BenutzerDaten benutzerdaten = benutzerverwaltung.aendereBenutzerSelbst(benutzerSelbstAendern);

        return mapper.map(benutzerdaten, BenutzerModel.class);
    }

    @Override
    public BenutzerModel aendereBenutzer(BenutzerBearbeitenModel model)
        throws BenutzerverwaltungBusinessException {
        BenutzerModel benutzer = model.getBenutzer();
        List<RolleDaten> rolleDaten = model.getAlleRollen().stream()
            .filter(rolle -> model.getSelektierteRollenIds().contains(rolle.getRollenId()))
            .map(rolle -> new RolleDaten(rolle.getRollenId(), rolle.getRollenName()))
            .collect(Collectors.toList());

        // Der neue Benutzername wird nur dann gesetzt, wenn er sich wirklich vom alten Namen unterscheidet.
        String neuerBenutzername =
            Objects.equals(model.getAlterBenutzername(), model.getBenutzer().getBenutzername()) ?
                null :
                model.getBenutzer().getBenutzername();

        BenutzerAendern benutzerAendern =
            new BenutzerAendern(model.getAlterBenutzername(), neuerBenutzername, benutzer.getStatus(),
                rolleDaten, benutzer.getNachname(), benutzer.getVorname(), benutzer.getBehoerde(),
                benutzer.getEmailAdresse(), benutzer.getTelefonnummer(), benutzer.getBemerkung());

        BenutzerDaten benutzerDaten = benutzerverwaltung.aendereBenutzer(benutzerAendern);

        return mapper.map(benutzerDaten, BenutzerModel.class);
    }
}
