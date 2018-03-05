package club.javalearn.crm.security.provider;

import club.javalearn.crm.model.User;
import club.javalearn.crm.service.UserService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-02-27
 **/
@Slf4j
@Getter
@Setter
public class SaltAuthenticationProvider extends DaoAuthenticationProvider {


    private PasswordEncoder myPasswordEncoder;

    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        logger.info("登录用户名:" + token.getName());
        System.out.println(authentication.getPrincipal());
        UserDetails user = getUserDetailsService().loadUserByUsername(token.getName());
        if (user == null) {
            throw new UsernameNotFoundException("用户名/密码无效");
        } else if (!user.isEnabled()) {
            throw new DisabledException("用户已被禁用");
        } else if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException("账号已过期");
        } else if (!user.isAccountNonLocked()) {
            throw new LockedException("账号已被锁定");
        } else if (!user.isCredentialsNonExpired()) {
            throw new LockedException("凭证已过期");
        }

        logger.info("登录用户名:" + token.getName());
        User dbUser = userService.findUserByName(token.getName());
        String loginPwd = getMyPasswordEncoder().encode(dbUser.getSalt()+ token.getCredentials().toString());
        if(!loginPwd.equals(dbUser.getPassword())){
            throw new BadCredentialsException("Invalid username/password");
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("user",user);
        System.out.println("密码解析器:" + this.getMyPasswordEncoder().getClass().getName());
        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        //return super.authenticate(authentication);
    }
}
