package club.javalearn.crm.web.controller.user;

import club.javalearn.crm.model.User;
import club.javalearn.crm.repository.UserRepository;
import club.javalearn.crm.service.UserService;
import club.javalearn.crm.utils.DataTableMessage;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
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
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/page"})
    public ModelAndView page(){
        return new ModelAndView("user/user");
    }


    @GetMapping
    @ApiOperation(value = "用户查询服务")
    public DataTableMessage<User> query(User user,@PageableDefault Pageable pageable) {
        log.debug("查询参数:"+user);
        return userService.getList(user,pageable);
    }

}
