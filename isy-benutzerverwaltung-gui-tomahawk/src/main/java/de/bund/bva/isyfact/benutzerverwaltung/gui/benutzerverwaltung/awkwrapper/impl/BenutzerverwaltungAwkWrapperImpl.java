package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.impl;

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
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.*;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.RolleSortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.RolleSuchkriterien;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.Rollenverwaltung;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.daten.RolleAendern;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.daten.RolleAnlegen;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.BenutzerverwaltungAwkWrapper;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.daten.BenutzerAnlegenDaten;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.awkwrapper.daten.BenutzerBearbeitenSelbst;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzerbearbeiten.BenutzerBearbeitenModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzersuchen.BenutzerSuchkriterienModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model.RolleModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.SuchergebnisModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.rollenverwaltung.rollesuchen.RolleSuchkriterienModel;
import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private Benutzerverwaltung benutzerverwaltung;

    private Rollenverwaltung rollenverwaltung;

    /**
     * Zugriff auf Dozer.
     */
    private Mapper mapper;

    /**
     * Sets the field 'benutzerverwaltung'.
     *
     * @param benutzerverwaltung New value for benutzerverwaltung
     */
    public void setBenutzerverwaltung(Benutzerverwaltung benutzerverwaltung) {
        this.benutzerverwaltung = benutzerverwaltung;
    }

    public void setRollenverwaltung(Rollenverwaltung rollenverwaltung) {
        this.rollenverwaltung = rollenverwaltung;
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
    public RolleModel legeRolleAn(RolleModel rolleModel) throws BenutzerverwaltungValidationException {
        RolleAnlegen rolleAnlegen = new RolleAnlegen(rolleModel.getRollenId(), rolleModel.getRollenName());
        return mapper.map(rollenverwaltung.legeRolleAn(rolleAnlegen), RolleModel.class);
    }

    @Override
    public RolleModel leseRolle(String rolleId) throws BenutzerverwaltungBusinessException {
        return mapper.map(rollenverwaltung.leseRolle(rolleId), RolleModel.class);
    }

    @Override
    public SuchergebnisModel<RolleModel> sucheRollen(RolleSuchkriterienModel filter, Sortierung sortierung,
        Paginierung paginierung) throws BenutzerverwaltungValidationException {
        RolleSuchkriterien coreSuchkriterien = mapper.map(filter, RolleSuchkriterien.class);

        // Sortierung mappen
        Sortierung coreSortierung =
            new Sortierung(RolleSortierattribut.getStandard(), Sortierrichtung.getStandard());
        if (sortierung != null) {
            coreSortierung.setAttribut(sortierung.getAttribut());
            coreSortierung.setRichtung(sortierung.getRichtung());
        }

        Suchergebnis<RolleDaten> suchergebnis =
            rollenverwaltung.sucheRollen(coreSuchkriterien, coreSortierung, paginierung);

        SuchergebnisModel<RolleModel> suchergebnisModel = new SuchergebnisModel<>();
        suchergebnis.getTrefferliste().forEach(
            treffer -> suchergebnisModel.getTrefferliste().add(mapper.map(treffer, RolleModel.class)));
        suchergebnisModel.setAnzahlTreffer(suchergebnis.getAnzahlTreffer());

        return suchergebnisModel;
    }

    @Override
    public RolleModel aendereRolle(String rolleId, RolleModel rolleModel)
        throws BenutzerverwaltungValidationException {
        // Neue ID nur mitliefern, wenn sie sich wirklich geändert hat.
        String neueRollenId = rolleId.equals(rolleModel.getRollenId()) ? null : rolleModel.getRollenId();
        RolleAendern rolleAendern = new RolleAendern(rolleId, neueRollenId, rolleModel.getRollenName());
        return mapper.map(rollenverwaltung.aendereRolle(rolleAendern), RolleModel.class);
    }

    @Override
    public List<RolleModel> getRollen() {
        try {
            return sucheRollen(new RolleSuchkriterienModel(),
                new Sortierung(RolleSortierattribut.getStandard(), Sortierrichtung.getStandard()), null)
                .getTrefferliste();
        } catch (BenutzerverwaltungValidationException e) {
            LOG.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public void loescheBenutzer(BenutzerModel benutzer) throws BenutzerverwaltungBusinessException {
        benutzerverwaltung.loescheBenutzer(benutzer.getBenutzername());
    }

    @Required
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
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
    public void loescheRolle(RolleModel rolle) throws BenutzerverwaltungBusinessException {
        rollenverwaltung.loescheRolle(rolle.getRollenId());
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
