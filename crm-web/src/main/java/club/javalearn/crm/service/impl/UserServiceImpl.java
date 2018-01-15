package club.javalearn.crm.service.impl;

import club.javalearn.crm.model.User;
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
import org.springframework.data.domain.Pageable;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public Message<User> getList(final String param, Pageable pageable) {
        BootstrapMessage<User> message = new BootstrapMessage<>();
        Page<User> page = userRepository.findAll(new Specification<User>(){
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<String> nickNamePath = root.get("nickName");
                Path<String> userNamePath = root.get("userName");
                Path<String> statusPath = root.get("status");
                List<Predicate> wherePredicate = new ArrayList<>();
                final User user = convertUser(param);
                if(user!=null){
                    if(StringUtils.isNoneBlank(user.getNickName())){
                        wherePredicate.add(cb.like(nickNamePath,"%"+user.getNickName()+"%"));
                    }
                    if(StringUtils.isNoneBlank(user.getUserName())){
                        wherePredicate.add(cb.like(userNamePath,"%"+user.getUserName()+"%"));
                    }
                    if(StringUtils.isNoneBlank(user.getStatus())){
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
        },pageable);

        List<User> userList = new ArrayList<>();
        for(User user1:page.getContent()){
            userList.add(new User(user1.getUserId(),
                    user1.getUserName(),
                    user1.getNickName(),
                    user1.getStatus(),
                    user1.getCreateDate(),
                    user1.getUpdateDate()));
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
        userRepository.updateUser(user.getUserId(),user.getUserName(),user.getNickName(),new Date());
    }

    @Modifying
    @Transactional(rollbackOn = RuntimeException.class)
    @Override
    public User create(User user) {
        if(StringUtils.isNoneBlank(user.getUserName())){
            if(userRepository.getByUserName(user.getUserName())!=null){
                throw new RuntimeException("用户名已存在");
            } else{
                user.setStatus(Constant.USER_DEFAULT_STATUS);
                user.setCreateDate(new Date());
                user.setUpdateDate(new Date());
                user.setSalt(SaltGenerator.createSalt());
                user.setPassword(passwordEncoder.encode(user.getSalt()+securityProperties.getBrowser().getDefaultPassword()));
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

    @Override
    public void deleteBatchByStatus(String userIds) {
        List<Long> userList = new ArrayList<>();
        String[] ids = userIds.split(",");
        for (String id:ids){
            userList.add(Long.parseLong(id));
        }
        userRepository.deleteByStatus(userList);
    }


    private User convertUser(String param){
        User user = null;
        if(StringUtils.isNoneBlank(param)){
            String[] values = param.split("&");
            user = new User();
            for (String value:values){
                String[] keys = value.split("=");
                if(keys.length==2){
                    if(keys[0].endsWith("userName") && StringUtils.isNoneBlank(keys[1])){
                        user.setUserName(keys[1].trim());
                    }
                    if(keys[0].endsWith("nickName") && StringUtils.isNoneBlank(keys[1])){
                        user.setNickName(keys[1].trim());
                    }
                    if(keys[0].endsWith("status") && StringUtils.isNoneBlank(keys[1]) && !"-1".endsWith(keys[1])){
                        user.setStatus(keys[1].trim());
                    }
                }
            }
        }

        return user;

    }
}
