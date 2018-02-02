package club.javalearn.crm.service.impl;

import club.javalearn.crm.model.Role;
import club.javalearn.crm.repository.RoleRepository;
import club.javalearn.crm.service.RoleService;
import club.javalearn.crm.utils.BootstrapMessage;
import club.javalearn.crm.utils.Constant;
import club.javalearn.crm.utils.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * @date 2018-01-16
 **/
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Message<Role> getList(final String param, Pageable pageable) {
        BootstrapMessage<Role> message = new BootstrapMessage<>();
        Page<Role> page = roleRepository.findAll(new Specification<Role>() {

            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<String> roleNamePath = root.get("roleName");
                Path<String> statusPath = root.get("status");
                List<Predicate> wherePredicate = new ArrayList<>();
                final Role role = convertRole(param);
                if (role != null) {
                    if (StringUtils.isNoneBlank(role.getRoleName())) {
                        wherePredicate.add(cb.like(roleNamePath, "%" + role.getRoleName() + "%"));
                    }
                    if (StringUtils.isNoneBlank(role.getStatus())) {
                        wherePredicate.add(cb.equal(statusPath, role.getStatus()));
                    }
                }
                Predicate[] predicates = new Predicate[]{};
                //这里可以设置任意条查询条件
                if (CollectionUtils.isNotEmpty(wherePredicate)) {
                    query.where(wherePredicate.toArray(predicates));
                }
                //这种方式使用JPA的API设置了查询条件，所以不需要再返回查询条件Predicate给Spring Data Jpa，故最后return null;即可。
                return null;
            }


        }, pageable);
        message.setRows(page.getContent());
        message.setLimit(page.getSize());
        message.setStart(page.getNumber());
        message.setTotal(page.getTotalElements());
        return message;
    }


    @Modifying
    @Transactional(rollbackOn = RuntimeException.class)
    @Override
    public void update(Role role) {
        Role oldRole = roleRepository.findOne(role.getRoleId());
        role.setCreateDate(oldRole.getCreateDate());
        role.setStatus(oldRole.getStatus());
        role.setUpdateDate(new Date());
        roleRepository.save(role);
    }

    @Modifying
    @Transactional(rollbackOn = RuntimeException.class)
    @Override
    public Role create(Role role) {
        role.setCreateDate(new Date());
        role.setStatus(Constant.DEFAULT_STATUS);
        return roleRepository.save(role);
    }

    @Modifying
    @Transactional(rollbackOn = RuntimeException.class)
    @Override
    public void deleteByStatus(Long roleId) {
        List<Long> roleList = new ArrayList<>();
        roleList.add(roleId);
        roleRepository.deleteBatch(roleList);
    }

    @Transactional(rollbackOn = RuntimeException.class)
    @Override
    public void deleteBatchByStatus(String roleIds) {
        log.info("批量删除角色");
        List<Long> roleList = new ArrayList<>();
        String[] ids = roleIds.split(",");
        for (String id:ids){
            roleList.add(Long.parseLong(id));
        }
        roleRepository.deleteBatch(roleList);
    }

    private Role convertRole(String param) {
        Role role;
        if (StringUtils.isNoneBlank(param)) {
            String[] values = param.split("&");
            role = new Role();
            for (String value : values) {
                String[] keys = value.split("=");
                if (keys.length == 2) {
                    if (keys[0].endsWith("roleName") && StringUtils.isNoneBlank(keys[1])) {
                        role.setRoleName(keys[1].trim());
                    }
                    if (keys[0].endsWith("status") && StringUtils.isNoneBlank(keys[1])&& !"-1".equals(keys[1])) {
                        role.setStatus(keys[1].trim());
                    }
                }
            }
        }else{
            role = new Role();
            role.setStatus(Constant.DEFAULT_STATUS);
        }
        return role;
    }
}
