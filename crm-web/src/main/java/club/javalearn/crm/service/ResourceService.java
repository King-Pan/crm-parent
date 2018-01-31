package club.javalearn.crm.service;

import club.javalearn.crm.model.Resource;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-01-18
 **/
public interface ResourceService {
    /**
     * 查询所有资源信息
     * @param param 查询参数
     * @return List
     */
    List<Resource> getList(String param);

    /**
     * 修改资源
     * @param resource 资源信息
     */
    void update(Resource resource);

    /**
     * 创建资源
     * @param resource 用户信息
     * @return 新增后的资源信息,带resourceId
     */
    Resource create(Resource resource);


    /**
     * 状态删除资源
     * @param resourceId 资源ID
     */
    void deleteByStatus(Long resourceId);

    /**
     * 批量状态删除资源
     * @param resourceds 资源ID
     */
    void deleteBatchByStatus(String resourceds);

    /**
     * 通过资源ID查找资源
     * @param resourceId
     * @return
     */
    Resource selectOne(String resourceId);
}
