package club.javalearn.crm.model;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QSysDataDict is a Querydsl query type for SysDataDict
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysDataDict extends EntityPathBase<SysDataDict> {

    private static final long serialVersionUID = 1926103092L;

    public static final QSysDataDict sysDataDict = new QSysDataDict("sysDataDict");

    public final DateTimePath<java.util.Date> createDate = createDateTime("createDate", java.util.Date.class);

    public final StringPath dictCode = createString("dictCode");

    public final StringPath dictDesc = createString("dictDesc");

    public final NumberPath<Long> dictId = createNumber("dictId", Long.class);

    public final StringPath dictType = createString("dictType");

    public final StringPath dictValue = createString("dictValue");

    public final StringPath parentId = createString("parentId");

    public final StringPath status = createString("status");

    public final DateTimePath<java.util.Date> updateDate = createDateTime("updateDate", java.util.Date.class);

    public QSysDataDict(String variable) {
        super(SysDataDict.class, forVariable(variable));
    }

    public QSysDataDict(Path<? extends SysDataDict> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysDataDict(PathMetadata metadata) {
        super(SysDataDict.class, metadata);
    }

}

