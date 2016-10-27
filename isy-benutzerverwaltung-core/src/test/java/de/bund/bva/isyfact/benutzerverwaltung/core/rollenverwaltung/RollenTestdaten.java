package de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung;

import java.util.Arrays;
import java.util.List;

/**
 * Stellt Testdaten für Tests mit Rollen zur Verfügung.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class RollenTestdaten {

    public static final String ROLLE_MITGLIED_ID = "MITGLIED";

    public static final String ROLLE_MITGLIED_NAME = "Standard-Rolle für neue Benutzer";

    public static final String ROLLE_ADMINISTRATOR_ID = "ADMINISTRATOR";

    public static final String ROLLE_ADMINISTRATOR_NAME = "Administrator-Rolle mit vollen Rechten";

    public static final String ROLLE_MODERATOR_ID = "MODERATOR";

    public static final String ROLLE_MODERATOR_NAME = "Administrator-Rolle mit Rechten zur Moderation";

    public static final List<String> ROLLEN_IDS =
        Arrays.asList(ROLLE_MITGLIED_ID, ROLLE_ADMINISTRATOR_ID, ROLLE_MODERATOR_ID);

    public static final List<String> ROLLEN_NAMEN =
        Arrays.asList(ROLLE_MITGLIED_NAME, ROLLE_ADMINISTRATOR_NAME, ROLLE_MODERATOR_NAME);

    private RollenTestdaten() {
    }

}
