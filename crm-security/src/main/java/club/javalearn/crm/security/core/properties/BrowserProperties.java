package club.javalearn.crm.security.core.properties;

import club.javalearn.crm.security.core.LoginType;
import lombok.Getter;
import lombok.Setter;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-01-04
 **/
@Getter
@Setter
public class BrowserProperties {
    private String loginPage = "/login";
    private LoginType loginType = LoginType.JSON;

}
