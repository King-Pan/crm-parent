package club.javalearn.crm.service.impl;

import club.javalearn.crm.model.Resource;
import club.javalearn.crm.repository.ResourceRepository;
import club.javalearn.crm.service.ResourceService;
import club.javalearn.crm.utils.Constant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-01-18
 **/
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public List<Resource> getList(final String param) {
        Sort sort = new Sort(Sort.Direction.ASC, "resourceOrder");
        return resourceRepository.findAll(new Specification<Resource>(){

            @Override
            public Predicate toPredicate(Root<Resource> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<String> resourceNamePath = root.get("resourceName");
                Path<String> statusPath = root.get("status");
                List<Predicate> wherePredicate = new ArrayList<>();
                final Resource resource = convertRole(param);
                if (resource != null) {
                    if (StringUtils.isNoneBlank(resource.getResourceName())) {
                        wherePredicate.add(cb.like(resourceNamePath, "%" + resource.getResourceName() + "%"));
                    }
                    if (StringUtils.isNoneBlank(resource.getStatus())) {
                        wherePredicate.add(cb.equal(statusPath, resource.getStatus()));
                    }
                }
                Predicate[] predicates = new Predicate[]{};
                //这里可以设置任意条查询条件
                if (CollectionUtils.isNotEmpty(wherePredicate)) {
                    query.where(wherePredicate.toArray(predicates));
                }
                return null;
            }
        },sort);
    }

    private Resource convertRole(String param){
        Resource resource = null;
        if(StringUtils.isNoneBlank(param)){
            String[] values = param.split("&");
            resource = new Resource();
            for (String value:values){
                String[] keys = value.split("=");
                if(keys.length==2){
                    if(keys[0].endsWith("resourceName") && StringUtils.isNoneBlank(keys[1])){
                        resource.setResourceName(keys[1].trim());
                    }
                    if(keys[0].endsWith("status") && StringUtils.isNoneBlank(keys[1]) && !"-1".equals(keys[1])){
                        resource.setStatus(keys[1].trim());
                    }
                }
            }
        }
        return resource;
    }
    @Transactional(rollbackOn = RuntimeException.class)
    @Modifying
    @Override
    public void update(Resource resource) {
        Resource oldResource = resourceRepository.findOne(resource.getResourceId());
        resource.setCreateDate(oldResource.getCreateDate());
        resource.setStatus(oldResource.getStatus());
        resource.setUpdateDate(new Date());
        resourceRepository.save(resource);
    }

    @Override
    public Resource create(Resource resource) {
        resource.setCreateDate(new Date());
        resource.setStatus(Constant.DEFAULT_STATUS);
        return resourceRepository.save(resource);
    }

    @Override
    public void deleteByStatus(Long resourceId) {
        Resource resource = resourceRepository.findOne(resourceId);
        resource.setStatus(Constant.DELETE_STATUS);
        resourceRepository.save(resource);
    }

    @Override
    public void deleteBatchByStatus(String resourceds) {

    }

    @Override
    public Resource selectOne(String resourceId) {
        return resourceRepository.findOne(Long.parseLong(resourceId));
    }
}
