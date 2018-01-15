package club.javalearn.crm.service;

import club.javalearn.crm.model.User;
import club.javalearn.crm.utils.DataTableMessage;
import club.javalearn.crm.utils.Message;
import org.springframework.data.domain.Pageable;


/**
 * crm-parent
 *
 * @author king-pan
 * @date  2017-12-09
 **/
public interface UserService {
    /**
     * 分页查询用户信息
     * @param param 查询条件
     * @param pageable 分页参数
     * @return DataTableMessage
     */
     Message<User> getList(String param, Pageable pageable);

    /**
     * 修改用户
     * @param user 用户信息
     */
    void update(User user);

    /**
     * 创建用户
     * @param user 用户信息
     * @return 新增后的用户信息,带userId
     */
     User create(User user);


    /**
     * 状态删除用户
     * @param userId 用户ID
     */
    void deleteByStatus(Long userId);

    /**
     * 批量状态删除用户
     * @param userIds 用户ID
     */
    void deleteBatchByStatus(String userIds);
}
