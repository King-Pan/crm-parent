package club.javalearn.crm.model;

import club.javalearn.crm.config.serializer.DefaultDateJsonSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.util.*;

/**
 * crm-parent
 *
 * @author king-pan
 * @create 2017-11-29
 **/
@Entity
@Table(name = "sys_user")
@Getter
@Setter
@ToString(exclude = {"roles"})
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(unique = true)
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
    @Column(length = 1)
    private String status;
    /**
     * 创建时间
     */
    @JsonSerialize(using = DefaultDateJsonSerializer.class)
    private Date createDate;
    /**
     * 最后更新时间
     */
    @JsonSerialize(using = DefaultDateJsonSerializer.class)
    private Date updateDate;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "sys_user_role",joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "roleId")})
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    @Transient
    private List<Long> roleIds = new ArrayList<>();


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            User user = (User)obj;
            return user.getUserId().equals(this.getUserId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getUserId().hashCode();
    }


    public User(Long userId,String userName,String nickName,String status,Date createDate,Date updateDate){
        this.userId = userId;
        this.userName = userName;
        this.nickName = nickName;
        this.status = status;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
