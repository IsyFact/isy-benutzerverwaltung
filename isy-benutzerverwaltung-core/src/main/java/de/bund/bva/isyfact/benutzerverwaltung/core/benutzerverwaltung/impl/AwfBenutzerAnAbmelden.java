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


import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungTechnicalRuntimeException;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.FehlerSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.BenutzerDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Benutzer;

import javax.validation.Validator;
import java.util.Date;

/**
 * Implementiert Anwendungsfälle zum An- und Abmelden von Benutzern. Hier wird nicht die eigentliche An- und
 * Abmeldung durchgeführt, sondern der Abschluss der Vorgänge im Datenmodell vermerkt.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
class AwfBenutzerAnAbmelden extends AbstractAnwendungsfall {

    AwfBenutzerAnAbmelden(BenutzerDao benutzerDao, Validator validator) {
        super(benutzerDao, validator);
    }

    Benutzer speichereErfolgreicheAnmeldung(String benutzername) throws BenutzerverwaltungBusinessException {
        if (benutzername == null) {
            throw new BenutzerverwaltungTechnicalRuntimeException(FehlerSchluessel.MSG_EINGABEDATEN_FEHLEN);
        }

        Benutzer benutzer = leseBenutzer(benutzername);
        benutzer.setFehlanmeldeVersuche(0);
        benutzer.setLetzteAnmeldung(new Date());
        return benutzer;
    }

    Benutzer speichereFehlgeschlageneAnmeldung(String benutzername)
        throws BenutzerverwaltungBusinessException {
        if (benutzername == null) {
            throw new BenutzerverwaltungTechnicalRuntimeException(FehlerSchluessel.MSG_EINGABEDATEN_FEHLEN);
        }

        Benutzer benutzer = leseBenutzer(benutzername);
        benutzer.setFehlanmeldeVersuche(benutzer.getFehlanmeldeVersuche() + 1);
        return benutzer;
    }

    Benutzer speichereAbmeldung(String benutzername) throws BenutzerverwaltungBusinessException {
        if (benutzername == null) {
            throw new BenutzerverwaltungTechnicalRuntimeException(FehlerSchluessel.MSG_EINGABEDATEN_FEHLEN);
        }

        Benutzer benutzer = leseBenutzer(benutzername);
        benutzer.setLetzteAbmeldung(new Date());
        return benutzer;
    }

}
