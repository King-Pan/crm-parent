package club.javalearn.crm.repository;

import club.javalearn.crm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * crm-parent
 *
 * @author king-pan
 * @create 2017-11-29
 **/
@Repository
public interface UserRepository extends JpaRepository<User,Long>,JpaSpecificationExecutor<User>,QueryDslPredicateExecutor<User> {

    /**
     * 通过用户名查找用户
     * @param userName 用户名
     * @return 用户
     */
    User getByUserName(String userName);

    /**
     * 更新用户信息
     * @param userId 用户ID
     * @param userName 用户名
     * @param nickName 用户昵称
     * @param updateDate 当前时间
     */
    @Modifying
    @Query(value = "update User u set u.userName = :userName,u.nickName = :nickName,u.updateDate = :updateDate where u.userId = :userId ")
    void updateUser(@Param("userId") Long userId,
                    @Param("userName") String userName,
                    @Param("nickName") String nickName,
                    @Param("updateDate") Date updateDate);

    /**
     * 状态删除用户
     * @param userIds 用户ID
     */
    @Modifying
    @Query(value = "update User u set u.status = 3  where u.userId in (:userIds) ")
    void deleteByStatus(@Param("userId") List<Long> userIds);


}
