package club.javalearn.crm.model;

import club.javalearn.crm.config.serializer.DefaultDateJsonSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-01-16
 **/
@Getter
@Setter
@ToString(exclude = {"users","resources"})
@Entity
@Table(name = "sys_role")
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    /**
     * 角色ID
     */
    @Id
    @GeneratedValue
    private Long roleId;

    /**
     * 角色名称
     */
    @Column(unique = true)
    private String roleName;

    /**
     * 角色状态
     */
    @Column(length = 1)
    private String status;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 创建时间
     */
    @JsonSerialize(using = DefaultDateJsonSerializer.class)
    private Date createDate;

    /**
     * 更新时间
     */
    @JsonSerialize(using = DefaultDateJsonSerializer.class)
    private Date updateDate;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users = new HashSet<>();


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "sys_role_resource",joinColumns ={@JoinColumn(name = "role_id", referencedColumnName = "roleId")},
            inverseJoinColumns =  {@JoinColumn(name = "resource_id", referencedColumnName = "resourceId")})
    @JsonIgnore
    private Set<Resource> resources = new HashSet<>();
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Role){
            Role role =(Role)obj;
            return role.getRoleId().equals(this.getRoleId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getRoleId().hashCode();
    }
}
