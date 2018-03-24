package club.javalearn.crm.security.authorize;

import club.javalearn.crm.security.core.properties.SecurityConstants;
import club.javalearn.crm.security.core.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 核心模块的授权配置提供器，安全模块涉及的url的授权配置在这里。
 *
 * @author king-pan
 * @date 2018-03-05
 **/
@Component
@Order
public class CrmAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    private SecurityProperties securityProperties;
    {
        System.out.println("-----------------------------------------------------------");
    }

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config
                .antMatchers(HttpMethod.GET, "/fonts/**").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/**/*.html",
                        "/admin/me",
                        "/resource").authenticated()
                .anyRequest().access("@rbacService.hasPermision(request,authentication)");
        return true;
    }

}
