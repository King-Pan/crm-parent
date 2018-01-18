package club.javalearn.crm.model;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QResource is a Querydsl query type for Resource
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QResource extends EntityPathBase<Resource> {

    private static final long serialVersionUID = 2060239271L;

    public static final QResource resource = new QResource("resource");

    public final DateTimePath<java.util.Date> createDate = createDateTime("createDate", java.util.Date.class);

    public final StringPath expression = createString("expression");

    public final StringPath level = createString("level");

    public final StringPath modelName = createString("modelName");

    public final StringPath parentId = createString("parentId");

    public final StringPath resourceDesc = createString("resourceDesc");

    public final NumberPath<Long> resourceId = createNumber("resourceId", Long.class);

    public final StringPath resourceName = createString("resourceName");

    public final SetPath<Role, QRole> roles = this.<Role, QRole>createSet("roles", Role.class, QRole.class, PathInits.DIRECT2);

    public final StringPath status = createString("status");

    public final DateTimePath<java.util.Date> updateDate = createDateTime("updateDate", java.util.Date.class);

    public QResource(String variable) {
        super(Resource.class, forVariable(variable));
    }

    public QResource(Path<? extends Resource> path) {
        super(path.getType(), path.getMetadata());
    }

    public QResource(PathMetadata metadata) {
        super(Resource.class, metadata);
    }

}

