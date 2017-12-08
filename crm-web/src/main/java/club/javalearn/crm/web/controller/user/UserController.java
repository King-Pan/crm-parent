package club.javalearn.crm.web.controller.user;

import club.javalearn.crm.model.User;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(value = {"","/"})
    public ModelAndView page(){
        return new ModelAndView("user/user");
    }


    @PostMapping
    @JsonView(User.UserSimpleView.class)
    @ApiOperation(value = "用户查询服务")
    public List<User> query(User user,
                            @PageableDefault(page = 2, size = 17, sort = "username,asc") Pageable pageable) {
        log.debug("查询参数:"+user);
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getSort());
        List<User> users = new ArrayList<>();

        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }

}
