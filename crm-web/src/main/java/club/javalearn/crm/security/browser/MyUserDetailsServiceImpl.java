package club.javalearn.crm.security.browser;

import club.javalearn.crm.service.UserService;
import club.javalearn.crm.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2017-12-28
 **/
@Slf4j
@Component
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("登录用户名:" + userName);
        //TODO 根据用户名查找用户信息
        club.javalearn.crm.model.User user = userService.findUserByName(userName);

        //https://github.com/King-Pan/security/find/master
        //根据查找到的用户信息判断用户状态
        return new User(userName,user.getPassword(),user.getStatus().equals(Constant.DEFAULT_STATUS),true,true,true,AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
        //return new User(userName,password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
