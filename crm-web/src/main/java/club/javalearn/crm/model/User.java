package club.javalearn.crm.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * crm-parent
 *
 * @author king-pan
 * @create 2017-11-29
 **/
@Entity
@Table(name = "sys_user")
@Data
public class User {

    /**
     * 用户编号
     */
    @Id
    @GeneratedValue
    private Long userId;
    /**
     * 用户名称
     */

    private String userName;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 加密盐
     */
    private String salt;
    /**
     * 用户状态
     */
    private String status;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 最后更新时间
     */
    private Date updateDate;

}
