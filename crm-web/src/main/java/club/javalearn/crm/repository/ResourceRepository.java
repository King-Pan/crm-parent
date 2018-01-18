package club.javalearn.crm.repository;

import club.javalearn.crm.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-01-18
 **/
public interface ResourceRepository extends JpaRepository<Resource,Long>,JpaSpecificationExecutor<Resource>,QueryDslPredicateExecutor<Resource> {
}
