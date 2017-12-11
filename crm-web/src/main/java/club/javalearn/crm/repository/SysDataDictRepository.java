package club.javalearn.crm.repository;

import club.javalearn.crm.model.SysDataDict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * crm-parent
 *
 * @author king-pan
 * @create 2017-12-10
 **/
@Repository
public interface SysDataDictRepository extends JpaRepository<SysDataDict,Long>,JpaSpecificationExecutor<SysDataDict>,QueryDslPredicateExecutor<SysDataDict> {

    @Query(value = "select * from sysDataDict b GROUP BY b.dictType where b.parentId is not null", nativeQuery = true)
    List<SysDataDict> getAllType();
}
