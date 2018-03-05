package club.javalearn.crm.security.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2017-12-28
 **/
@RestController
public class DefaultUserController {

    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }

    @GetMapping("/loginError")
    public ModelAndView loginError(){
        ModelAndView view = new ModelAndView("login");
        view.addObject("param.error",true);
        return view;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        ModelAndView view = new ModelAndView("login");
        view.addObject("logout",true);
        return view;
    }
}
