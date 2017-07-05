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


import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierrichtung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.KonfigurationsSchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.NamedQuerySchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSuchkriterien;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.BenutzerDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Benutzer;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.QBenutzer;
import de.bund.bva.pliscommon.konfiguration.common.Konfiguration;
import de.bund.bva.pliscommon.persistence.dao.AbstractDao;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementiert den Datenbankzugriff für Benutzer mittels JPA.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
public class JpaBenutzerDao extends AbstractDao<Benutzer, Long> implements BenutzerDao, InitializingBean {

    private static QBenutzer BENUTZER = QBenutzer.benutzer;

    private JPAQueryFactory queryFactory;

    private final Konfiguration konfiguration;

    public JpaBenutzerDao(Konfiguration konfiguration) {
        this.konfiguration = konfiguration;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        queryFactory = new JPAQueryFactory(getEntityManager());
    }

    @Override
    public void loesche(Benutzer benutzer) {
        long id = benutzer.getId();
        Benutzer persistenterBenutzer = sucheMitId(id);
        super.loesche(persistenterBenutzer);
    }

    @Override
    public Benutzer sucheMitBenutzername(String benutzername) {
        return super.getSingleOptionalResult(getEntityManager()
            .createNamedQuery(NamedQuerySchluessel.GET_BENUTZER_BENUTZERNAME, Benutzer.class)
            .setParameter("benutzername", benutzername));
    }

    @Override
    public List<Benutzer> sucheMitBenutzerFilter(BenutzerSuchkriterien suchkriterien, Sortierung sortierung,
        Paginierung paginierung) {
        /*
         * Default error handling
         */
        if (suchkriterien == null || sortierung == null) {
            return Collections.emptyList();
        }

        JPAQuery<Benutzer> query = queryFactory.selectFrom(BENUTZER);

        // Benutzername
        query.where(erzeugeSuchfilter(suchkriterien));

        // Sortierung
        OrderSpecifier<?> standardSortierung;
        if (Sortierrichtung.getStandard().isAufsteigend()) {
            standardSortierung = BenutzerSortierattribut.getStandard().getAttribut().asc();
        } else {
            standardSortierung = BenutzerSortierattribut.getStandard().getAttribut().desc();
        }

        ComparableExpression<?> sortByExpression = sortierung.getAttribut();
        if (sortByExpression == null) {
            query.orderBy(standardSortierung);
        } else {
            if (sortierung.getRichtung().isAbsteigend()) {
                query.orderBy(sortByExpression.desc(), standardSortierung);
            } else {
                query.orderBy(sortByExpression.asc(), standardSortierung);
            }
        }

        // Paginierung
        if (paginierung != null) {
            query.offset(paginierung.getErsterTreffer()).limit(paginierung.getTrefferProSeite());
        }

        return query.fetch();
    }

    @Override
    public long zaehleMitKriterien(BenutzerSuchkriterien suchkriterien) {
        /*
         * Default error handling
         */
        if (suchkriterien == null) {
            suchkriterien = new BenutzerSuchkriterien();
        }

        JPAQuery<Benutzer> query = queryFactory.selectFrom(BENUTZER);
        query.where(erzeugeSuchfilter(suchkriterien));

        return query.fetchCount();
    }

    /**
     * Erzeugt den Suchfilter auf Basis der Suchkriterien.
     *
     * @param suchkriterien Suchkriterien
     */
    private Predicate[] erzeugeSuchfilter(BenutzerSuchkriterien suchkriterien) {
        List<Predicate> predicates = new ArrayList<>();

        boolean sucheCaseSensitive = konfiguration.getAsBoolean(KonfigurationsSchluessel.SUCHE_CASE_SENSITIVE, false);

        if (sucheCaseSensitive) {
            if (suchkriterien.getBenutzername() != null && !suchkriterien.getBenutzername().isEmpty()) {
                predicates.add(BENUTZER.benutzername.like("%" + suchkriterien.getBenutzername() + "%"));
            }

            if (suchkriterien.getNachname() != null && !suchkriterien.getNachname().isEmpty()) {
                predicates.add(BENUTZER.nachname.like("%" + suchkriterien.getNachname() + "%"));
            }

            if (suchkriterien.getVorname() != null && !suchkriterien.getVorname().isEmpty()) {
                predicates.add(BENUTZER.vorname.like("%" + suchkriterien.getVorname() + "%"));
            }

            if (suchkriterien.getBehoerde() != null && !suchkriterien.getBehoerde().isEmpty()) {
                predicates.add(BENUTZER.behoerde.like("%" + suchkriterien.getBehoerde() + "%"));
            }
        } else {
            if (suchkriterien.getBenutzername() != null && !suchkriterien.getBenutzername().isEmpty()) {
                predicates.add(BENUTZER.benutzername.likeIgnoreCase("%" + suchkriterien.getBenutzername() + "%"));
            }

            if (suchkriterien.getNachname() != null && !suchkriterien.getNachname().isEmpty()) {
                predicates.add(BENUTZER.nachname.likeIgnoreCase("%" + suchkriterien.getNachname() + "%"));
            }

            if (suchkriterien.getVorname() != null && !suchkriterien.getVorname().isEmpty()) {
                predicates.add(BENUTZER.vorname.likeIgnoreCase("%" + suchkriterien.getVorname() + "%"));
            }

            if (suchkriterien.getBehoerde() != null && !suchkriterien.getBehoerde().isEmpty()) {
                predicates.add(BENUTZER.behoerde.likeIgnoreCase("%" + suchkriterien.getBehoerde() + "%"));
            }
        }

        if (suchkriterien.getStatus() != null) {
            predicates.add(BENUTZER.status.eq(suchkriterien.getStatus()));
        }

        if (suchkriterien.getRollenId() != null && !suchkriterien.getRollenId().isEmpty()) {
            predicates.add(BENUTZER.rollen.any().id.eq(suchkriterien.getRollenId()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
