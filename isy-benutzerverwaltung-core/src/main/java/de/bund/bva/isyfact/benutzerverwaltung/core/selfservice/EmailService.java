package de.bund.bva.isyfact.benutzerverwaltung.core.selfservice;

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

import javax.mail.Message;
import javax.mail.MessagingException;

import de.bund.bva.isyfact.benutzerverwaltung.common.exception.BenutzerverwaltungBusinessException;

/**
 * Hilfsklasse zum Versenden der Emails.
 *
 * @author Bjoern Saxe, msg systems ag
 * @author Alexander Salvanos, msg systems ag
 *
 */
public interface EmailService {

    /**
     * Erzeugt eine Email, über den der Benutzer einen Token-Link erhält.
     *
     * @param emailAdresse Die Emailadresse des Empfängers
     * @param token Das Token für den Link.
     * @throws MessagingException
     */
    Message erzeugeEmailMitToken(String emailAdresse, String token) throws BenutzerverwaltungBusinessException;

    /**
     * Versendet eine Email an den Benutzer.
     *
     * @param message Die Emailnachricht für den Benutzer.
     * @return  true, wenn die Email verschickt werden konnte.
     *          false, wenn die Email nicht verschickt werden konnte.
     * @throws BenutzerverwaltungBusinessException
     */
    boolean sendeEmail(Message message) throws BenutzerverwaltungBusinessException;
}
