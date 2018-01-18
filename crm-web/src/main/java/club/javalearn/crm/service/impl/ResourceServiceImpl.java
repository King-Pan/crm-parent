package club.javalearn.crm.service.impl;

import club.javalearn.crm.model.Resource;
import club.javalearn.crm.repository.ResourceRepository;
import club.javalearn.crm.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return resourceRepository.findAll();
    }

    @Override
    public void update(Resource resource) {
        /*
         * select * from sys_resource t where t.parent_id is null;

         select * from sys_resource t where t.parent_id = 1;
         */
    }

    @Override
    public Resource create(Resource resource) {
        return null;
    }

    @Override
    public void deleteByStatus(Long resourceId) {

    }

    @Override
    public void deleteBatchByStatus(String resourceds) {

    }
}
