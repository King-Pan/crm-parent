package club.javalearn.crm.security.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-01-04
 **/
@Getter
@Setter
@ConfigurationProperties(prefix = "basic.security")
public class SecurityProperties {
    private BrowserProperties browser = new BrowserProperties();

}
