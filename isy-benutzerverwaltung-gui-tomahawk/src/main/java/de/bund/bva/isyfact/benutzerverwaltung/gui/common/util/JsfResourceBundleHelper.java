package de.bund.bva.isyfact.benutzerverwaltung.gui.common.util;

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

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Eine Helper-Klasse für den Umgang mit JSF-Resourcen. Stellt erweiterte Funktionalität (z.B.
 * Parameterersetzung) bereit.
 *
 * @author Capgemini, Andreas Hörning
 */
public class JsfResourceBundleHelper {

    public String erzeugeNachricht(ResourceBundle bundle, String msgKey, String paramValue) {
        String msgValue = bundle.getString(msgKey);
        MessageFormat messageFormat = new MessageFormat(msgValue);
        Object[] args = { paramValue };
        return messageFormat.format(args);
    }

}
