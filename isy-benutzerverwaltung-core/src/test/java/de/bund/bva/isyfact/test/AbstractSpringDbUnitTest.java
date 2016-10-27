package de.bund.bva.isyfact.test;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(
    { DependencyInjectionTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@Transactional
public abstract class AbstractSpringDbUnitTest {

    @PersistenceContext
    private EntityManager em;

    /**
     * Übermittelt alle vom Entity Manager zwischengespeicherten Operationen an die Datenbank. Dies ist
     * notwendig, damit DBUnit die Datenbank nach dem Test korrekt mit den erwarteten Daten vergleichen kann.
     */
    @After
    public void commit() {
        em.flush();
    }

}
