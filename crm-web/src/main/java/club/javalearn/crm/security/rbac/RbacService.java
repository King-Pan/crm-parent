package club.javalearn.crm.security.rbac;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-02-28
 **/
public interface RbacService {
  boolean  hasPermision(HttpServletRequest request, Authentication authentication);
}
