package club.javalearn.crm.security.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
}
