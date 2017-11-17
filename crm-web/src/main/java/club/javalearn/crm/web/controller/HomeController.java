package club.javalearn.crm.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * crm-parent
 *
 * @author king-pan
 * @create 2017-11-18
 **/
@RestController
public class HomeController {

    @RequestMapping(value = {"/","/index","/home"})
    public ModelAndView home(){
        return new ModelAndView("index");
    }
}
