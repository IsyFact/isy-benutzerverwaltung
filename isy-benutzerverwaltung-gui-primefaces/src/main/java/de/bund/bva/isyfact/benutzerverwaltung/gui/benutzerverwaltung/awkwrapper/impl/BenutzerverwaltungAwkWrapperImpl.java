package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.impl;

/*-
 * #%L
 * IsyFact Benutzerverwaltung GUI mit Primefaces
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
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierrichtung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Suchergebnis;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungValidationException;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSuchkriterien;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.Benutzerverwaltung;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.*;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.RolleSortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.RolleSuchkriterien;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.Rollenverwaltung;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.BenutzerverwaltungAwkWrapper;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.daten.BenutzerAnlegenDaten;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzerbearbeiten.BenutzerBearbeitenModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzerselbstbearbeiten.BenutzerSelbstBearbeitenModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzersuchen.BenutzerSuchkriterienModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.passwortaendern.PasswortAendernModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.daten.PasswortZuruecksetzenDaten;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.RolleModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.SuchergebnisModel;
import org.dozer.Mapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Standard-Implementierung des AWK-Wrappers für die Komponente Benutzerverwaltung.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
public class BenutzerverwaltungAwkWrapperImpl implements BenutzerverwaltungAwkWrapper {

    private final Benutzerverwaltung benutzerverwaltung;

    private final Rollenverwaltung rollenverwaltung;

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
        for (BenutzerDaten treffer : suchergebnis.getTrefferliste()) {
            suchergebnisModel.getTrefferliste().add(mapper.map(treffer, BenutzerModel.class));
        }
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
    public BenutzerModel setzePasswort(PasswortAendernModel model)
        throws BenutzerverwaltungBusinessException {
        PasswortAendern passwortAendern =
            new PasswortAendern(model.getBenutzername(), model.getAltesPasswort(), model.getNeuesPasswort(),
                model.getNeuesPasswortBestaetigung());
        return mapper.map(benutzerverwaltung.setzePasswort(passwortAendern), BenutzerModel.class);
    }

    @Override
    public BenutzerModel setzePasswortZurueck(PasswortZuruecksetzenDaten daten)
        throws BenutzerverwaltungBusinessException {
        PasswortZuruecksetzen passwortZuruecksetzen =
            new PasswortZuruecksetzen(daten.getBenutzername(), daten.getNeuesPasswort(),
                daten.getNeuesPasswortBestaetigung());
        return mapper
            .map(benutzerverwaltung.setzePasswortZurueck(passwortZuruecksetzen), BenutzerModel.class);
    }

    @Override
    public BenutzerModel setzeStatus(String benutzername, BenutzerStatus neuerStatus)
        throws BenutzerverwaltungBusinessException {
        return mapper.map(benutzerverwaltung.setzeStatus(benutzername, neuerStatus), BenutzerModel.class);
    }

    @Override
    public BenutzerModel aendereBenutzer(BenutzerBearbeitenModel model)
        throws BenutzerverwaltungBusinessException {
        // Sammele alle selektierten Rollen aus dem Model.
        List<RolleDaten> rollen = model.getAlleRollen().stream()
            .filter(rolle -> model.getSelektierteRollenIds().contains(rolle.getRollenId()))
            .map(rolle -> new RolleDaten(rolle.getRollenId(), rolle.getRollenName()))
            .collect(Collectors.toList());

        // Der neue Benutzername wird nur dann gesetzt, wenn er sich wirklich vom alten Namen unterscheidet.
        String neuerBenutzername =
            Objects.equals(model.getAlterBenutzername(), model.getNeuerBenutzername()) ?
                null :
                model.getNeuerBenutzername();

        BenutzerAendern benutzerAendern = new BenutzerAendern(model.getAlterBenutzername(), neuerBenutzername,
            model.getSelektierterBenutzerStatus(), rollen, model.getNachname(), model.getVorname(),
            model.getBehoerde(), model.getEmailAdresse(), model.getTelefonnummer(), model.getBemerkung());
        return mapper.map(benutzerverwaltung.aendereBenutzer(benutzerAendern), BenutzerModel.class);
    }

    @Override
    public BenutzerModel aendereBenutzerSelbst(BenutzerSelbstBearbeitenModel model)
        throws BenutzerverwaltungBusinessException {
        BenutzerSelbstAendern benutzerSelbstAendern =
            new BenutzerSelbstAendern(model.getBenutzername(), model.getNachname(), model.getVorname(),
                model.getBehoerde(), model.getEmailAdresse(), model.getTelefonnummer());
        return mapper
            .map(benutzerverwaltung.aendereBenutzerSelbst(benutzerSelbstAendern), BenutzerModel.class);
    }

    @Override
    public void loescheBenutzer(BenutzerModel benutzer) throws BenutzerverwaltungBusinessException {
        benutzerverwaltung.loescheBenutzer(benutzer.getBenutzername());
    }

    @Override
    public BenutzerModel speichereAbmeldung(String benutzername) throws BenutzerverwaltungBusinessException {
        return mapper.map(benutzerverwaltung.speichereAbmeldung(benutzername), BenutzerModel.class);
    }

    @Override
    public void weiseRolleZu(RolleModel model, List<String> benutzernamen)
        throws BenutzerverwaltungBusinessException {
        RolleDaten rolle = new RolleDaten(model.getRollenId(), model.getRollenName());
        benutzerverwaltung.weiseRolleZu(rolle, benutzernamen);
    }

    @Override
    public void entzieheRolle(RolleModel model, List<String> benutzernamen)
        throws BenutzerverwaltungBusinessException {
        RolleDaten rolle = new RolleDaten(model.getRollenId(), model.getRollenName());
        benutzerverwaltung.entzieheRolle(rolle, benutzernamen);
    }

    @Override
    public List<RolleModel> leseAlleRollen() throws BenutzerverwaltungValidationException {
        Suchergebnis<RolleDaten> suchergebnis = rollenverwaltung.sucheRollen(new RolleSuchkriterien(),
            new Sortierung(RolleSortierattribut.getStandard(), Sortierrichtung.getStandard()), null);

        return suchergebnis.getTrefferliste().stream()
            .map(rolle -> new RolleModel(rolle.getRollenId(), rolle.getRollenName()))
            .collect(Collectors.toList());
    }
}
