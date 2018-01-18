package club.javalearn.crm.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 数据字典实体
 *
 * @author king-pan
 * @date 2017-12-10
 **/
@Entity
@Table(name = "sys_data_dict")
@Getter
@Setter
@ToString
public class SysDataDict {
    /**
     * 数据字典ID
     */
    @Id
    @GeneratedValue
    private Long dictId;
    /**
     * 数据字典类型
     */
    private String dictType;

    /**
     * 数据字典父类ID
     */
    private String parentId;
    /**
     * 数据字典编码
     */
    private String dictCode;
    /**
     * 数据字典值
     */
    @Column(unique = true)
    private String dictValue;
    /**
     * 数据字典描述
     */
    private String dictDesc;
    /**
     * 状态
     */
    private String status;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;
}
