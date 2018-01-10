package club.javalearn.crm.web.controller.user;

import club.javalearn.crm.common.ServerResponse;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
        return new ModelAndView("user/user");
    }


    /**
     *
     * @param param 查询参数
     * @param pageable 分页参数
     * @return 封装的用户分页数据
     */
    @GetMapping
    @ApiOperation(value = "用户查询服务")
    public Message<User> query(@RequestParam(name = "param",required = false) String  param, @PageableDefault Pageable pageable) {
        log.debug("查询参数:"+param);
        return userService.getList(param,pageable);
    }


    @PutMapping("/{userId:\\d+}")
    @ApiOperation(value = "用户修改服务")
    public ServerResponse update(@PathVariable String userId,@Valid @RequestBody User user, BindingResult errors) {
        ServerResponse response;
        try {

            response = ServerResponse.createBySuccessMessage("用户修改成功");
        }catch (Exception e){
            log.error("用户修改失败",e);
            response = ServerResponse.createByErrorMessage("用户修改失败:"+e.getMessage());
        }
        System.out.println(userId);
        System.out.println(user);
        return response;
    }

    @PostMapping
    @ApiOperation(value = "用户创建服务")
    public ServerResponse create() {
        ServerResponse response = ServerResponse.createBySuccessMessage("用户创建成功");
        return response;
    }

    @DeleteMapping("/{userId:\\d+}")
    public void delete(@PathVariable String userId) {
        System.out.println(userId);
    }

    public static void main(String[] args) {
        String str = "${targetProject}\\java\\${basePackage}\\${moduleName}\\entity\\";
        System.out.println(str.replaceAll("\\\\", File.separator));
    }

}
