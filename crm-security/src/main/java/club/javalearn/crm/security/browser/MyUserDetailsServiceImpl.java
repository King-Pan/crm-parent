package club.javalearn.crm.security.browser;

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

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("登录用户名:" + userName);
        //TODO 根据用户名查找用户信息
        String password = passwordEncoder.encode("123456");

        //根据查找到的用户信息判断用户状态
        return new User(userName,password,true,true,true,true,AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        //return new User(userName,password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
