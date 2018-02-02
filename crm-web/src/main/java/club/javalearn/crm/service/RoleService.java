package club.javalearn.crm.service;

import club.javalearn.crm.model.Role;
import club.javalearn.crm.utils.Message;
import org.springframework.data.domain.Pageable;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-01-16
 **/
public interface RoleService {
    /**
     * 分页查询用户信息
     * @param param 查询条件
     * @param pageable 分页参数
     * @return DataTableMessage
     */
    Message<Role> getList(String param, Pageable pageable);

    /**
     * 修改用户
     * @param role 角色信息
     */
    void update(Role role);

    /**
     * 创建用户
     * @param role 角色信息
     * @return 新增后的用户信息,带userId
     */
    Role create(Role role);


    /**
     * 状态删除角色
     * @param roleId 角色ID
     */
    void deleteByStatus(Long roleId);

    /**
     * 批量状态删除角色
     * @param roleIds 用户ID
     */
    void deleteBatchByStatus(String roleIds);
}
