package de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung;

import de.bund.bva.isyfact.benutzerverwaltung.core.TestfallKonfiguration;
import de.bund.bva.isyfact.test.AbstractSpringDbUnitTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = TestfallKonfiguration.class)
public abstract class AbstractRollenverwaltungTest extends AbstractSpringDbUnitTest {

    @Autowired
    protected Rollenverwaltung rollenverwaltung;

}
