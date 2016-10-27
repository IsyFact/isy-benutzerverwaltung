package de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.jpa;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Paginierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierrichtung;
import de.bund.bva.isyfact.benutzerverwaltung.common.datentyp.Sortierung;
import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.NamedQuerySchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSortierattribut;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerSuchkriterien;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.dao.BenutzerDao;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.Benutzer;
import de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity.QBenutzer;
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

    @Override
    public void afterPropertiesSet() throws Exception {
        queryFactory = new JPAQueryFactory(getEntityManager());
    }

    /**
     * {@inheritDoc}
     */
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

        // Benutzername
        if (suchkriterien.getBenutzername() != null && !suchkriterien.getBenutzername().isEmpty()) {
            predicates.add(BENUTZER.benutzername.like("%" + suchkriterien.getBenutzername() + "%"));
        }

        // Nachname
        if (suchkriterien.getNachname() != null && !suchkriterien.getNachname().isEmpty()) {
            predicates.add(BENUTZER.nachname.like("%" + suchkriterien.getNachname() + "%"));
        }

        // Vorname
        if (suchkriterien.getVorname() != null && !suchkriterien.getVorname().isEmpty()) {
            predicates.add(BENUTZER.vorname.like("%" + suchkriterien.getVorname() + "%"));
        }

        // Behoerde
        if (suchkriterien.getBehoerde() != null && !suchkriterien.getBehoerde().isEmpty()) {
            predicates.add(BENUTZER.behoerde.like("%" + suchkriterien.getBehoerde() + "%"));
        }

        // Status
        if (suchkriterien.getStatus() != null) {
            predicates.add(BENUTZER.status.eq(suchkriterien.getStatus()));
        }

        // RollenZuweisung (Alle Benutzer mit dieser Rollenzuweisung)
        if (suchkriterien.getRollenId() != null && !suchkriterien.getRollenId().isEmpty()) {
            predicates.add(BENUTZER.rollen.any().id.eq(suchkriterien.getRollenId()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
