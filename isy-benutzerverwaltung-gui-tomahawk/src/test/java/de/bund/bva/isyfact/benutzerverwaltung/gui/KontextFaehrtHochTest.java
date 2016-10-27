package de.bund.bva.isyfact.benutzerverwaltung.gui;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.benutzersuchen.BenutzerSuchenController;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestfallKonfiguration.class)
public class KontextFaehrtHochTest {

    @Autowired
    private BenutzerSuchenController benutzerSuchenController;

    @Test
    public void testFaehrtHoch() {
        Assert.assertNotNull(benutzerSuchenController);
    }
}
