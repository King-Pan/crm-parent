package club.javalearn.crm.security.rbac;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-02-28
 **/
public class RbacServiceImpl implements RbacService {
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        return false;
    }
}
