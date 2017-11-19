package club.javalearn.crm.web.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * crm-parent
 *
 * @author king-pan
 * @create 2017-11-19
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = {"","/"})
    public ModelAndView page(){
        return new ModelAndView("user/user");
    }


}
