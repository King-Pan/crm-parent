package club.javalearn.crm.service;

import club.javalearn.crm.model.User;
import club.javalearn.crm.utils.DataTableMessage;
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
     * @param user 查询条件
     * @param pageable 分页参数
     * @return
     */
    public DataTableMessage<User> getList(User user, Pageable pageable);
}
