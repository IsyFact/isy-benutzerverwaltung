package de.bund.bva.isyfact.test;

/*-
 * #%L
 * IsyFact Benutzerverwaltung Core
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
