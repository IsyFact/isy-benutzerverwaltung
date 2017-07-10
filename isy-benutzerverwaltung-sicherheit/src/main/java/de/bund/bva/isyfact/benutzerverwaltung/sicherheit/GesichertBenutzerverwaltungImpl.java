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
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSuchkriterien;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.Benutzerverwaltung;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.*;
import de.bund.bva.isyfact.benutzerverwaltung.integration.BenutzerReferenz;
import de.bund.bva.isyfact.benutzerverwaltung.sicherheit.konstanten.BenutzerverwaltungRecht;
import de.bund.bva.pliscommon.aufrufkontext.AufrufKontextVerwalter;
import de.bund.bva.pliscommon.sicherheit.annotation.Gesichert;

import java.util.List;

/**
 * Implementierung der Benutzerverwaltung, die alle Aufrufe mit den folgenden Rechten absichert:
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class GesichertBenutzerverwaltungImpl implements GesichertBenutzerverwaltung {

    private final Benutzerverwaltung benutzerverwaltung;

    private final AufrufKontextVerwalter<?> aufrufKontextVerwalter;

    public GesichertBenutzerverwaltungImpl(Benutzerverwaltung benutzerverwaltung,
        AufrufKontextVerwalter<?> aufrufKontextVerwalter) {
        this.benutzerverwaltung = benutzerverwaltung;
        this.aufrufKontextVerwalter = aufrufKontextVerwalter;
    }

    @Override
    public BenutzerDaten leseBenutzer(String benutzername) throws BenutzerverwaltungBusinessException {
        return benutzerverwaltung.leseBenutzer(benutzername);
    }

    @Override
    @Gesichert(BenutzerverwaltungRecht.BENUTZER_SUCHEN)
    public Suchergebnis<BenutzerDaten> sucheBenutzer(BenutzerSuchkriterien suchkriterien,
        Sortierung sortierung, Paginierung paginierung) throws BenutzerverwaltungValidationException {
        return benutzerverwaltung.sucheBenutzer(suchkriterien, sortierung, paginierung);
    }

    @Override
    @Gesichert(BenutzerverwaltungRecht.BENUTZER_ANLEGEN)
    public BenutzerDaten legeBenutzerAn(BenutzerAnlegen benutzerAnlegen)
        throws BenutzerverwaltungValidationException {
        return benutzerverwaltung.legeBenutzerAn(benutzerAnlegen);
    }

    @Override
    @Gesichert(BenutzerverwaltungRecht.BENUTZER_AENDERN_SELBST)
    public BenutzerDaten setzePasswort(PasswortAendern passwortAendern)
        throws BenutzerverwaltungBusinessException {
        return benutzerverwaltung.setzePasswort(passwortAendern);
    }

    @Override
    @Gesichert(BenutzerverwaltungRecht.BENUTZER_AENDERN)
    public BenutzerDaten setzePasswortZurueck(PasswortZuruecksetzen passwortZuruecksetzen)
        throws BenutzerverwaltungBusinessException {
        return benutzerverwaltung.setzePasswortZurueck(passwortZuruecksetzen);
    }

    @Override
    @Gesichert(BenutzerverwaltungRecht.BENUTZER_AENDERN)
    public BenutzerDaten setzeStatus(String benutzername, BenutzerStatus neuerStatus)
        throws BenutzerverwaltungBusinessException {
        return benutzerverwaltung.setzeStatus(benutzername, neuerStatus);
    }

    @Override
    @Gesichert(BenutzerverwaltungRecht.BENUTZER_AENDERN)
    public BenutzerDaten aendereBenutzer(BenutzerAendern benutzerAendern)
        throws BenutzerverwaltungBusinessException {
        return benutzerverwaltung.aendereBenutzer(benutzerAendern);
    }

    @Override
    @Gesichert(BenutzerverwaltungRecht.BENUTZER_AENDERN_SELBST)
    public BenutzerDaten aendereBenutzerSelbst(BenutzerSelbstAendern benutzerSelbstAendern)
        throws BenutzerverwaltungBusinessException {
        return benutzerverwaltung.aendereBenutzerSelbst(benutzerSelbstAendern);
    }

    @Override
    public BenutzerDaten speichereErfolgreicheAnmeldung(String benutzername)
        throws BenutzerverwaltungBusinessException {
        return benutzerverwaltung.speichereErfolgreicheAnmeldung(benutzername);
    }

    @Override
    public BenutzerDaten speichereFehlgeschlageneAnmeldung(String benutzername)
        throws BenutzerverwaltungBusinessException {
        return benutzerverwaltung.speichereFehlgeschlageneAnmeldung(benutzername);
    }

    @Override
    public BenutzerDaten speichereAbmeldung(String benutzername) throws BenutzerverwaltungBusinessException {
        return benutzerverwaltung.speichereAbmeldung(benutzername);
    }

    @Override
    @Gesichert(BenutzerverwaltungRecht.BENUTZER_LOESCHEN)
    public void loescheBenutzer(String benutzername) throws BenutzerverwaltungBusinessException {
        benutzerverwaltung.loescheBenutzer(benutzername);
    }

    @Override
    @Gesichert(BenutzerverwaltungRecht.BENUTZER_AENDERN)
    public void weiseRolleZu(RolleDaten rolle, List<String> benutzernamen)
        throws BenutzerverwaltungBusinessException {
        benutzerverwaltung.weiseRolleZu(rolle, benutzernamen);
    }

    @Override
    @Gesichert(BenutzerverwaltungRecht.BENUTZER_AENDERN)
    public void entzieheRolle(RolleDaten rolle, List<String> benutzernamen)
        throws BenutzerverwaltungBusinessException {
        benutzerverwaltung.entzieheRolle(rolle, benutzernamen);
    }

    @Override
    public void loeseBenutzerReferenzAuf(BenutzerReferenz benutzerReferenz) {
        benutzerverwaltung.loeseBenutzerReferenzAuf(benutzerReferenz);
    }

    @Override
    public BenutzerDaten selfServicePasswortZuruecksetzen(String email) throws BenutzerverwaltungBusinessException {
        return benutzerverwaltung.selfServicePasswortZuruecksetzen(email);
    }
}
