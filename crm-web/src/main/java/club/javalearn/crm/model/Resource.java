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
 * @date 2018-01-17
 **/
@Setter
@Getter
@ToString(exclude = {"roles"})
@Entity
@Table(name = "sys_resource")
@NoArgsConstructor
@AllArgsConstructor
public class Resource {

    /**
     * 资源ID
     */
    @Id
    @GeneratedValue
    private Long resourceId;

    /**
     * 资源名称
     */
    @Column(unique = true)
    private String resourceName;

    /**
     * 父类ID
     */
    private String parentId;

    /**
     * 上级资源名称
     */
    private String parentName;

    /**
     * url
     */
    private String url;

    /**
     * 图标
     */
    private String icon;

    /**
     * 资源类型
     */
    private String resourceType;

    /**
     * 排序
     */
    private Integer resourceOrder;

    /**
     * 资源表达式
     */
    private String expression;

    /**
     * 资源描述
     */
    private String resourceDesc;

    /**
     * 状态
     */
    @Column(length = 1)
    private String status;

    /**
     * 创建日期
     */
    @JsonSerialize(using = DefaultDateJsonSerializer.class)
    private Date createDate;

    /**
     * 更新日期
     */
    @JsonSerialize(using = DefaultDateJsonSerializer.class)
    private Date updateDate;

    @ManyToMany
    @JoinTable(name = "sys_role_resource",joinColumns = {@JoinColumn(name = "resource_id", referencedColumnName = "resourceId")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "roleId")})
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Resource){
            Resource resource = (Resource)obj;
            return  resource.getResourceId().equals(this.getResourceId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getResourceId().hashCode();
    }
}
