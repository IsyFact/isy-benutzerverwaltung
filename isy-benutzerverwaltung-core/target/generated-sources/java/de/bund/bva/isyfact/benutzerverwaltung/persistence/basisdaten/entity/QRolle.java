package de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRolle is a Querydsl query type for Rolle
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRolle extends EntityPathBase<Rolle> {

    private static final long serialVersionUID = 980197134L;

    public static final QRolle rolle = new QRolle("rolle");

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

    public final NumberPath<Long> primaryKey = createNumber("primaryKey", Long.class);

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QRolle(String variable) {
        super(Rolle.class, forVariable(variable));
    }

    public QRolle(Path<? extends Rolle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRolle(PathMetadata metadata) {
        super(Rolle.class, metadata);
    }

}

