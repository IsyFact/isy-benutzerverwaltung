package de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBenutzer is a Querydsl query type for Benutzer
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBenutzer extends EntityPathBase<Benutzer> {

    private static final long serialVersionUID = 1531014327L;

    public static final QBenutzer benutzer = new QBenutzer("benutzer");

    public final StringPath behoerde = createString("behoerde");

    public final StringPath bemerkung = createString("bemerkung");

    public final StringPath benutzername = createString("benutzername");

    public final StringPath emailAdresse = createString("emailAdresse");

    public final NumberPath<Integer> fehlanmeldeVersuche = createNumber("fehlanmeldeVersuche", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> letzteAbmeldung = createDateTime("letzteAbmeldung", java.util.Date.class);

    public final DateTimePath<java.util.Date> letzteAnmeldung = createDateTime("letzteAnmeldung", java.util.Date.class);

    public final StringPath nachname = createString("nachname");

    public final StringPath passwort = createString("passwort");

    public final ListPath<Rolle, QRolle> rollen = this.<Rolle, QRolle>createList("rollen", Rolle.class, QRolle.class, PathInits.DIRECT2);

    public final EnumPath<de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus> status = createEnum("status", de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus.class);

    public final StringPath telefonnummer = createString("telefonnummer");

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public final StringPath vorname = createString("vorname");

    public QBenutzer(String variable) {
        super(Benutzer.class, forVariable(variable));
    }

    public QBenutzer(Path<? extends Benutzer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBenutzer(PathMetadata metadata) {
        super(Benutzer.class, metadata);
    }

}

