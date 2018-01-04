package club.javalearn.crm.web.controller.user;

import club.javalearn.crm.model.User;
import club.javalearn.crm.repository.UserRepository;
import club.javalearn.crm.service.UserService;
import club.javalearn.crm.utils.DataTableMessage;
import club.javalearn.crm.utils.Message;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;


/**
 * crm-parent
 *
 * @author king-pan
 * @create 2017-11-19
 **/
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/page"})
    public ModelAndView page(HttpServletResponse response){
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        return new ModelAndView("user/user");
    }


    /**
     * $("#iframe_box").find(".show_iframe:hidden");
     * $($("#iframe_box").find(".show_iframe:hidden").document).find(".form-inline").serialize()
     * $("#iframe_box").find("iframe[src='user/page']").find(".form-inline").serialize()
     * @param user
     * @param pageable
     * @return
     */
    @GetMapping
    @ApiOperation(value = "用户查询服务")
    public Message<User> query(@RequestParam(name = "param",required = false) String  param, @PageableDefault Pageable pageable) {
        log.debug("查询参数:"+param);
        return userService.getList(param,pageable);
    }

    public static void main(String[] args) {
        String str = "${targetProject}\\java\\${basePackage}\\${moduleName}\\entity\\";
        System.out.println(str.replaceAll("\\\\", File.separator));
    }

}
