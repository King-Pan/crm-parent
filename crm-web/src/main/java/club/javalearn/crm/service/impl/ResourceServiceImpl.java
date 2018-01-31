package club.javalearn.crm.service.impl;

import club.javalearn.crm.model.Resource;
import club.javalearn.crm.model.Role;
import club.javalearn.crm.repository.ResourceRepository;
import club.javalearn.crm.service.ResourceService;
import club.javalearn.crm.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public List<Resource> getList() {
        Sort sort = new Sort(Sort.Direction.ASC, "resourceOrder");
        return resourceRepository.findAll(sort);
    }

    @Transactional(rollbackOn = RuntimeException.class)
    @Modifying
    @Override
    public void update(Resource resource) {
        Resource oldResoure = resourceRepository.findOne(resource.getResourceId());
        resource.setCreateDate(oldResoure.getCreateDate());
        resource.setStatus(oldResoure.getStatus());
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

    }

    @Override
    public void deleteBatchByStatus(String resourceds) {

    }

    @Override
    public Resource selectOne(String resourceId) {
        return resourceRepository.findOne(Long.parseLong(resourceId));
    }
}
