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

import java.util.UUID;

import de.bund.bva.isyfact.benutzerverwaltung.AbstractSicherheitTest;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Benutzer;
import de.bund.bva.isyfact.benutzerverwaltung.sicherheit.exception.BenutzerverwaltungAuthentifizierungFehlgeschlagenException;
import de.bund.bva.pliscommon.sicherheit.Berechtigungsmanager;
import de.bund.bva.pliscommon.sicherheit.Sicherheit;
import de.bund.bva.pliscommon.sicherheit.common.exception.AuthentifizierungFehlgeschlagenException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Tests, welche die erwartete Funktionalität der Isy-Sicherheit prüfen.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class IsySicherheitTest extends AbstractSicherheitTest {

    private static final String BENUTZERNAME = "Benutzer_1";

    private static final String PASSWORT = "geheim";

    private static final String PASSWORT_FALSCH = "falsch";

    private static final String KORRELATIONS_ID = UUID.randomUUID().toString();

    @Autowired
    private Sicherheit<BenutzerverwaltungAufrufKontextImpl> sicherheit;

    @Test
    public void testAuthentifizierungErfolgreichMitHash()
        throws BenutzerverwaltungAuthentifizierungFehlgeschlagenException {
        Benutzer bDb = erzeugeBenutzerInDb(BENUTZERNAME, PASSWORT, BenutzerStatus.AKTIVIERT, ROLLE_B);
        BenutzerverwaltungAufrufKontextImpl unauthentifizierterAufrufKontext =
            new BenutzerverwaltungAufrufKontextImpl();
        unauthentifizierterAufrufKontext.setDurchfuehrenderBenutzerKennung(BENUTZERNAME);
        unauthentifizierterAufrufKontext.setDurchfuehrenderBenutzerPasswort(bDb.getPasswort());
        unauthentifizierterAufrufKontext.setPasswortIstHash(true);
        unauthentifizierterAufrufKontext.setKorrelationsId(KORRELATIONS_ID);

        // Authentifizierung mit Klartext-Passwort
        Berechtigungsmanager bm =
            sicherheit.getBerechtigungsManagerUndAuthentifiziere(unauthentifizierterAufrufKontext);
        assertNotNull(bm);
        assertFalse(bm.hatRecht(RECHT_A));
        assertTrue(bm.hatRecht(RECHT_B));
    }

    @Test(expected = AuthentifizierungFehlgeschlagenException.class)
    public void testAuthentifizierungFehlgehlschagenMitHash()
        throws BenutzerverwaltungAuthentifizierungFehlgeschlagenException {
        Benutzer bDb = erzeugeBenutzerInDb(BENUTZERNAME, PASSWORT, BenutzerStatus.AKTIVIERT);
        BenutzerverwaltungAufrufKontextImpl unauthentifizierterAufrufKontext =
            new BenutzerverwaltungAufrufKontextImpl();
        unauthentifizierterAufrufKontext.setDurchfuehrenderBenutzerKennung(bDb.getBenutzername());
        unauthentifizierterAufrufKontext.setDurchfuehrenderBenutzerPasswort(PASSWORT_FALSCH);
        unauthentifizierterAufrufKontext.setPasswortIstHash(true);
        unauthentifizierterAufrufKontext.setKorrelationsId(KORRELATIONS_ID);

        // Authentifizierung mit Klartext-Passwort
        Berechtigungsmanager bm =
            sicherheit.getBerechtigungsManagerUndAuthentifiziere(unauthentifizierterAufrufKontext);
        assertNull(bm);
    }

    @Test
    public void testAuthentifizierungErfolgreichOhneHash()
        throws BenutzerverwaltungAuthentifizierungFehlgeschlagenException {
        erzeugeBenutzerInDb(BENUTZERNAME, PASSWORT, BenutzerStatus.AKTIVIERT, ROLLE_A, ROLLE_B);
        BenutzerverwaltungAufrufKontextImpl unauthentifizierterAufrufKontext =
            new BenutzerverwaltungAufrufKontextImpl();
        unauthentifizierterAufrufKontext.setDurchfuehrenderBenutzerKennung(BENUTZERNAME);
        unauthentifizierterAufrufKontext.setDurchfuehrenderBenutzerPasswort(PASSWORT);
        unauthentifizierterAufrufKontext.setPasswortIstHash(false);
        unauthentifizierterAufrufKontext.setKorrelationsId(KORRELATIONS_ID);

        // Authentifizierung mit Klartext-Passwort
        Berechtigungsmanager bm =
            sicherheit.getBerechtigungsManagerUndAuthentifiziere(unauthentifizierterAufrufKontext);
        assertNotNull(bm);
        assertTrue(bm.hatRecht(RECHT_A));
        assertTrue(bm.hatRecht(RECHT_B));
    }

    @Test(expected = AuthentifizierungFehlgeschlagenException.class)
    public void testAuthentifizierungFehlgehlschagenOhneHash()
        throws BenutzerverwaltungAuthentifizierungFehlgeschlagenException {
        erzeugeBenutzerInDb(BENUTZERNAME, PASSWORT, BenutzerStatus.AKTIVIERT);
        BenutzerverwaltungAufrufKontextImpl unauthentifizierterAufrufKontext =
            new BenutzerverwaltungAufrufKontextImpl();
        unauthentifizierterAufrufKontext.setDurchfuehrenderBenutzerKennung(BENUTZERNAME);
        unauthentifizierterAufrufKontext.setDurchfuehrenderBenutzerPasswort(PASSWORT_FALSCH);
        unauthentifizierterAufrufKontext.setPasswortIstHash(true);
        unauthentifizierterAufrufKontext.setKorrelationsId(KORRELATIONS_ID);

        // Authentifizierung mit Klartext-Passwort
        Berechtigungsmanager bm =
            sicherheit.getBerechtigungsManagerUndAuthentifiziere(unauthentifizierterAufrufKontext);
        assertNull(bm);
    }
}
