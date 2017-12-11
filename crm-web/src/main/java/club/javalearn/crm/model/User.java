package club.javalearn.crm.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * 用户编号
     */
    @Id
    @GeneratedValue
    @JsonView(UserSimpleView.class)
    private Long userId;
    /**
     * 用户名称
     */
    @JsonView(UserSimpleView.class)
    private String userName;
    /**
     * 用户昵称
     */
    @JsonView(UserSimpleView.class)
    private String nickName;
    /**
     * 用户密码
     */
    @JsonView(UserDetailView.class)
    private String password;
    /**
     * 加密盐
     */
    @JsonView(UserDetailView.class)
    private String salt;
    /**
     * 用户状态
     */
    @JsonView(UserSimpleView.class)
    private String status;
    /**
     * 创建时间
     */
    @JsonView(UserSimpleView.class)
    private Date createDate;
    /**
     * 最后更新时间
     */
    @JsonView(UserSimpleView.class)
    private Date updateDate;

    public User(Long userId,String userName,String nickName,String status,Date createDate,Date updateDate){
        this.userId = userId;
        this.userName = userName;
        this.nickName = nickName;
        this.status = status;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public interface UserSimpleView{}

    public interface UserDetailView extends UserSimpleView{}

}
