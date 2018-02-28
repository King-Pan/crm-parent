package club.javalearn.crm.service.impl;

import club.javalearn.crm.model.Role;
import club.javalearn.crm.model.User;
import club.javalearn.crm.repository.RoleRepository;
import club.javalearn.crm.repository.UserRepository;
import club.javalearn.crm.security.core.properties.SecurityProperties;
import club.javalearn.crm.service.UserService;
import club.javalearn.crm.utils.BootstrapMessage;
import club.javalearn.crm.utils.Constant;
import club.javalearn.crm.utils.Message;
import club.javalearn.crm.utils.SaltGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
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
 * @date  2017-12-09
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public Message<User> getList(final User user, Pageable pageable) {
        BootstrapMessage<User> message = new BootstrapMessage<>();
        Sort sort = new Sort(Sort.Direction.DESC, "updateDate");
        sort.and(new Sort(Sort.Direction.ASC,"status"));
        sort.and(new Sort(Sort.Direction.ASC,"userId"));
        Pageable pageableRequest = new PageRequest(pageable.getPageNumber(),pageable.getPageSize() , sort);
        Page<User> page = userRepository.findAll(new Specification<User>(){
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<String> nickNamePath = root.get("nickName");
                Path<String> userNamePath = root.get("userName");
                Path<String> statusPath = root.get("status");
                List<Predicate> wherePredicate = new ArrayList<>();
                //final User user = convertUser(param);
                if(user!=null){
                    if(StringUtils.isNoneBlank(user.getNickName())){
                        wherePredicate.add(cb.like(nickNamePath,"%"+user.getNickName()+"%"));
                    }
                    if(StringUtils.isNoneBlank(user.getUserName())){
                        wherePredicate.add(cb.like(userNamePath,"%"+user.getUserName()+"%"));
                    }
                    if(StringUtils.isNoneBlank(user.getStatus()) && !Constant.ALL_STATUS.equals(user.getStatus())){
                        wherePredicate.add(cb.equal(statusPath,user.getStatus()));
                    }
                }

                Predicate[] predicates = new Predicate[]{};
                //这里可以设置任意条查询条件
                if (CollectionUtils.isNotEmpty(wherePredicate)){
                    query.where(wherePredicate.toArray(predicates));
                }
                //这种方式使用JPA的API设置了查询条件，所以不需要再返回查询条件Predicate给Spring Data Jpa，故最后return null;即可。
                return null;
            }
        },pageableRequest);

        List<User> userList = new ArrayList<>();
        for(User user1:page.getContent()){
            User newUser = new User(user1.getUserId(),
                    user1.getUserName(),
                    user1.getNickName(),
                    user1.getStatus(),
                    user1.getCreateDate(),
                    user1.getUpdateDate());
            List<Long> roleIds = new ArrayList<>();
            for (Role role:user1.getRoles()){
                roleIds.add(role.getRoleId());
            }
            newUser.setRoleIds(roleIds);
            userList.add(newUser);
        }
        message.setRows(userList);
        message.setLimit(page.getSize());
        message.setStart(page.getNumber());
        message.setTotal(page.getTotalElements());
        return message;
    }

    @Transactional(rollbackOn = RuntimeException.class)
    @Override
    public void update(User user) {
        List<Role> roles = roleRepository.findByRoleIdIn(user.getRoleIds());

        User newUser = userRepository.findOne(user.getUserId());
        newUser.setStatus(user.getStatus());
        newUser.setUpdateDate(new Date());
        newUser.setUserName(user.getUserName());
        newUser.setNickName(user.getNickName());
        newUser.getRoles().removeAll(newUser.getRoles());
        for (Role role: roles){
            newUser.getRoles().add(role);
            role.getUsers().add(user);
        }
        userRepository.save(newUser);
    }

    @Modifying
    @Transactional(rollbackOn = RuntimeException.class)
    @Override
    public User create(User user) {
        if(StringUtils.isNoneBlank(user.getUserName())){
            if(userRepository.getByUserName(user.getUserName())!=null){
                throw new RuntimeException("用户名已存在");
            } else{
                List<Role> roles = roleRepository.findByRoleIdIn(user.getRoleIds());
                for (Role role: roles){
                    user.getRoles().add(role);
                }
                user.setStatus(Constant.DEFAULT_STATUS);
                user.setCreateDate(new Date());
                user.setSalt(SaltGenerator.createSalt());
                user.setPassword(passwordEncoder.encode(securityProperties.getBrowser().getDefaultPassword()));
                return userRepository.save(user);
            }
        }else{
            throw new RuntimeException("用户名不能为空");
        }
    }

    @Transactional(rollbackOn = RuntimeException.class)
    @Override
    public void deleteByStatus(Long userId) {
        List<Long> userIds = new ArrayList<>();
        userIds.add(userId);
        userRepository.deleteByStatus(userIds);
    }

    @Transactional(rollbackOn = RuntimeException.class)
    @Override
    public void deleteBatchByStatus(String userIds) {
        List<Long> userList = new ArrayList<>();
        String[] ids = userIds.split(",");
        for (String id:ids){
            userList.add(Long.parseLong(id));
        }
        userRepository.deleteByStatus(userList);
    }

    @Override
    public User findUserByName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
