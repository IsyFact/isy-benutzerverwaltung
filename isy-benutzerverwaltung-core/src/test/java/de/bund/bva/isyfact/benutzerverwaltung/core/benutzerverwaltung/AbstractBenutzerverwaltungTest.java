package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung;

import de.bund.bva.isyfact.benutzerverwaltung.core.TestfallKonfiguration;
import de.bund.bva.isyfact.test.AbstractSpringDbUnitTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = TestfallKonfiguration.class)
public abstract class AbstractBenutzerverwaltungTest extends AbstractSpringDbUnitTest {

    @Autowired
    protected Benutzerverwaltung benutzerverwaltung;

}
