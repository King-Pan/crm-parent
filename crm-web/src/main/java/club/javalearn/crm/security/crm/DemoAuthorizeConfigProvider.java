package club.javalearn.crm.security.crm;

import club.javalearn.crm.security.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-03-05
 **/
@Component
@Order(value = Integer.MIN_VALUE)
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {
    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        //demo项目授权配置
        config.anyRequest().access("@rbacService.hasPermission(request,authentication)");
        return false;
    }
}
