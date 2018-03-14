package club.javalearn.crm.repository;

import club.javalearn.crm.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-01-16
 **/
@Repository
public interface RoleRepository extends JpaRepository<Role,Long>,JpaSpecificationExecutor<Role> ,QueryDslPredicateExecutor<Role> {

    /**
     * 批量状态删除角色
     * @param roleIds 角色ID集合
     */
    @Modifying
    @Query(value = "update Role r set r.status=3 where r.roleId in (:roleIds)")
    void deleteBatch(@Param("roleIds") List<Long> roleIds);

    /**
     * 通过roleIds查询选择的角色
     * @param roleIds 角色ID
     * @return 角色稽核
     */
    List<Role> findByRoleIdIn(List<Long> roleIds);

    //@Query(value = "select ")
    //List<Long> getResourceList(Long roleId);
}
