package de.bund.bva.isyfact.benutzerverwaltung.gui.selfservice.awkwrapper.impl;

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


import com.github.dozermapper.core.Mapper;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.daten.PasswortZuruecksetzen;
import de.bund.bva.isyfact.benutzerverwaltung.core.selfservice.SelfService;
import de.bund.bva.isyfact.benutzerverwaltung.gui.common.model.BenutzerModel;
import de.bund.bva.isyfact.benutzerverwaltung.gui.selfservice.awkwrapper.SelfServiceAwkWrapper;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Standard-Implementierung des AWK-Wrappers für die Komponente SelfService.
 *
 * @author Bjoern Saxe, msg systems ag
 * @author Alexander Salvanos, msg systems ag
 */
@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
public class SelfServiceAwkWrapperImpl implements SelfServiceAwkWrapper {

    private final SelfService selfService;

    private final Mapper mapper;

    public SelfServiceAwkWrapperImpl(SelfService selfService, Mapper mapper) {
        this.selfService = selfService;
        this.mapper = mapper;
    }

    @Override
    public boolean sendePasswortZuruecksetzenEmail(String emailadresse)
        throws BenutzerverwaltungBusinessException {
        return selfService.sendePasswortZuruecksetzenEmail(emailadresse);
    }

    @Override
    public String holeBenutzernameZuToken(String token) throws BenutzerverwaltungBusinessException {
        return selfService.holeBenutzernameZuToken(token);
    }

    @Override
    public BenutzerModel passwortZuruecksetzen(String benutzername, String passwort,
        String passwortWiederholung) throws BenutzerverwaltungBusinessException {
        PasswortZuruecksetzen passwortZuruecksetzen =
            new PasswortZuruecksetzen(benutzername, passwort, passwortWiederholung);
        return mapper.map(selfService.setzePasswortZurueck(passwortZuruecksetzen), BenutzerModel.class);
    }

}
