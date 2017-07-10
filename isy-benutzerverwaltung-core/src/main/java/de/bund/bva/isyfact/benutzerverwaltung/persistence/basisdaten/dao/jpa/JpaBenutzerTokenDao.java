package de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.jpa;

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

import com.querydsl.jpa.impl.JPAQueryFactory;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.BenutzerTokenDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.BenutzerToken;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.QBenutzerToken;
import de.bund.bva.pliscommon.persistence.dao.AbstractDao;
import org.springframework.beans.factory.InitializingBean;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;

/**
 * Datenzugriff für das BenutzerToken.
 *
 * @author Bjoern Saxe, msg systems ag
 * @author Alexander Salvanos, msg systems ag
 */
public class JpaBenutzerTokenDao extends AbstractDao<BenutzerToken, String> implements BenutzerTokenDao, InitializingBean {

    private static QBenutzerToken BENUTZER_TOKEN = QBenutzerToken.benutzerToken;

    private JPAQueryFactory queryFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        queryFactory = new JPAQueryFactory(getEntityManager());
    }

    @Override
    public void loescheTokensFuerBenutzer(String benutzerName) {
        EntityManager em = getEntityManager();
        Query query =
                em.createQuery(
                        "DELETE FROM BenutzerToken b " +
                       "WHERE b.benutzer.id in (" +
                       "SELECT b.id FROM Benutzer b " +
                       "WHERE b.benutzername = :benutzerName)");
        query.setParameter("benutzerName", benutzerName);
        query.executeUpdate();
    }

    @Override
    public void loescheTokensVorDatum(Date datum) {
        queryFactory.delete(BENUTZER_TOKEN).where(BENUTZER_TOKEN.ablaufDatum.before(datum)).execute();
    }
}
