package club.javalearn.crm.repository;

import club.javalearn.crm.model.Resource;
import club.javalearn.crm.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-01-18
 **/
public interface ResourceRepository extends JpaRepository<Resource,Long>,JpaSpecificationExecutor<Resource>,QueryDslPredicateExecutor<Resource> {

    /**
     * 统计该节点下是否有子节点
     * @param resourceId 父类ID
     * @return 子节点个数
     */
    @Query(value = "select count(t) from Resource t where t.parentId = :resourceId ")
    int hasChild(@Param("resourceId")Long resourceId);


    /**
     * 通过resourceId查询资源列表
     * @param resourceIds 资源IDlist
     * @return 资源列表
     */
    List<Resource> findByResourceIdIn(List<Long> resourceIds);
}
