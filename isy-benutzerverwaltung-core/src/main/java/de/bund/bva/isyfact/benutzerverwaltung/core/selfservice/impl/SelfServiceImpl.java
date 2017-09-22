package de.bund.bva.isyfact.benutzerverwaltung.core.selfservice.impl;

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

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.BenutzerDaten;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.Benutzerverwaltung;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.PasswortZuruecksetzen;
import de.bund.bva.isyfact.benutzerverwaltung.core.selfservice.EmailService;
import de.bund.bva.isyfact.benutzerverwaltung.core.selfservice.SelfService;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.BenutzerDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.selfservice.dao.BenutzerTokenDao;
import de.bund.bva.pliscommon.konfiguration.common.Konfiguration;

/**
 * Implementierungsklasse vom SelfService.
 *
 * @author Bjoern Saxe, msg systems ag
 * @author Alexander Salvanos, msg systems ag
 *
 */
public class SelfServiceImpl implements SelfService {

    private AwfPasswortZuruecksetzen awfPasswortZuruecksetzen;

    public SelfServiceImpl(
            Benutzerverwaltung benutzerverwaltung,
            BenutzerTokenDao benutzerTokenDao,
            BenutzerDao benutzerDao,
            EmailService emailService,
            Konfiguration konfiguration) {
        awfPasswortZuruecksetzen = new AwfPasswortZuruecksetzen(benutzerverwaltung, benutzerTokenDao, benutzerDao,
                                                                emailService, konfiguration);
    }

    @Override
    public boolean sendePasswortZuruecksetzenEmail(String emailadresse) throws BenutzerverwaltungBusinessException {
        return awfPasswortZuruecksetzen.sendePasswortZuruecksetzenEmail(emailadresse);
    }


    @Override
    public String holeBenutzernameZuToken(String token) throws BenutzerverwaltungBusinessException {
        return awfPasswortZuruecksetzen.holeBenutzernameZuToken(token);
    }

    @Override
    public BenutzerDaten setzePasswortZurueck(PasswortZuruecksetzen passwortZuruecksetzen)
            throws BenutzerverwaltungBusinessException {
        return awfPasswortZuruecksetzen.setzePasswortZurueck(passwortZuruecksetzen);
    }
}
