package de.bund.bva.isyfact.benutzerverwaltung.gui.common.util;

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
