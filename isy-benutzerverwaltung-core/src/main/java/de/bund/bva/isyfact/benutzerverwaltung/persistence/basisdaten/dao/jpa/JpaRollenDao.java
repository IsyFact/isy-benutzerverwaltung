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

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.NamedQuerySchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.RolleSortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.RolleSuchkriterien;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.RollenDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.QRolle;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Rolle;
import de.bund.bva.pliscommon.persistence.dao.AbstractDao;
import org.springframework.beans.factory.InitializingBean;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Implementiert den Datenbankzugriff für Rollen mittels JPA.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
public class JpaRollenDao extends AbstractDao<Rolle, Long> implements RollenDao, InitializingBean {

    private static QRolle ROLLE = QRolle.rolle;

    private JPAQueryFactory queryFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        queryFactory = new JPAQueryFactory(getEntityManager());
    }

    @Override
    public Rolle sucheMitRollenId(String rollenId) {
        TypedQuery<Rolle> query =
            getEntityManager().createNamedQuery(NamedQuerySchluessel.SUCHE_ROLLE_ID, Rolle.class);
        query.setParameter("id", rollenId);
        return getSingleOptionalResult(query);
    }

    @Override
    public List<Rolle> sucheMitRollenIds(Collection<String> rollenIds) {
        TypedQuery<Rolle> query =
            getEntityManager().createNamedQuery(NamedQuerySchluessel.SUCHE_ROLLE_IDS, Rolle.class);
        query.setParameter("ids", rollenIds);
        return query.getResultList();
    }

    @Override
    public List<Rolle> sucheMitFilter(RolleSuchkriterien suchkriterien, Sortierung sortierung,
        Paginierung paginierung) {
        /*
         * Default error handling
         */
        if (suchkriterien == null || sortierung == null) {
            return Collections.emptyList();
        }

        JPAQuery<Rolle> query = queryFactory.selectFrom(ROLLE);
        query.where(erzeugeSuchfilter(suchkriterien));

        // Sortierung
        ComparableExpression<?> sortByExpression;
        if (sortierung.getAttribut() == null) {
            sortByExpression = RolleSortierattribut.getStandard().getAttribut();
        } else {
            sortByExpression = sortierung.getAttribut();
        }

        if (sortierung.getRichtung().isAbsteigend()) {
            query.orderBy(sortByExpression.desc());
        } else {
            query.orderBy(sortByExpression.asc());
        }

        // Paginierung
        if (paginierung != null) {
            query.offset(paginierung.getErsterTreffer()).limit(paginierung.getTrefferProSeite());
        }

        return query.fetch();
    }

    @Override
    public long zaehleMitKriterien(RolleSuchkriterien suchkriterien) {
        /*
         * Default error handling
         */
        if (suchkriterien == null) {
            suchkriterien = new RolleSuchkriterien();
        }

        JPAQuery<Rolle> query = queryFactory.selectFrom(ROLLE);
        query.where(erzeugeSuchfilter(suchkriterien));

        return query.fetchCount();
    }

    /**
     * Erzeugt den Suchfilter auf Basis der Suchkriterien.
     *
     * @param suchkriterien Suchkriterien
     */
    private Predicate[] erzeugeSuchfilter(RolleSuchkriterien suchkriterien) {
        List<Predicate> predicates = new ArrayList<>();

        // ID
        if (suchkriterien.getId() != null && !suchkriterien.getId().isEmpty()) {
            predicates.add(ROLLE.id.like("%" + suchkriterien.getId() + "%"));
        }

        // Name
        if (suchkriterien.getName() != null && !suchkriterien.getName().isEmpty()) {
            predicates.add(ROLLE.name.like("%" + suchkriterien.getName() + "%"));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
