package club.javalearn.crm.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
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

    @RequestMapping(value = {"/welcome"})
    public ModelAndView welcome(){
        return new ModelAndView("welcome");
    }

    @GetMapping("/hell")
    public String hello(){
        return "三生三世不见不会,zs，ls，万物,哈哈哈哈，我是爱你的，老婆";
    }


}
