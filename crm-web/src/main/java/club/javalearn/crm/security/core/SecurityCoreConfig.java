package club.javalearn.crm.security.core;

import club.javalearn.crm.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-01-04
 **/
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

}
