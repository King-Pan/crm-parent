package club.javalearn.crm.security.browser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2017-12-28
 **/
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.httpBasic()
        http.formLogin() //表单登录
                .loginPage("/login")
                .and()
                .authorizeRequests()
                //当访问/login.html不需要身份认证
                .antMatchers("/login").permitAll()
                //任何请求都需要身份认证
                .anyRequest().authenticated().and().csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/webjars/**");
        web.ignoring().antMatchers("/css/**","/fonts/**","/libs/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
