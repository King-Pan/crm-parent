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
 * @date  2017-11-19
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
    public ServerResponse update(@RequestBody User user) {
        ServerResponse response;
        try {
            userService.update(user);
            response = ServerResponse.createBySuccessMessage("用户修改成功");
        }catch (Exception e){
            log.error("用户修改失败",e);
            response = ServerResponse.createByErrorMessage("用户修改失败:"+e.getMessage());
        }
        if(log.isDebugEnabled()){
            log.debug("修改用户ID为{}.", user.getUserId());
            log.debug("修改后用户为:{}", user);
        }
        return response;
    }

    @PostMapping
    @ApiOperation(value = "用户创建服务")
    public ServerResponse create(@RequestBody User user) {
        ServerResponse response;
        try {
            userService.create(user);
            response = ServerResponse.createBySuccessMessage("用户创建成功");
        }catch (Exception e){
            log.error("用户修改失败",e);
            response = ServerResponse.createByErrorMessage("用户创建失败:"+e.getMessage());
        }
        if(log.isDebugEnabled()){
            log.debug("新增用户ID为{}.", user.getUserId());
            log.debug("新增用户为:{}", user);
        }
        return response;
    }

    @DeleteMapping("/{userId:\\d+}")
    @ApiOperation(value = "用户删除服务")
    public ServerResponse delete(@PathVariable("userId")Long userId) {
        ServerResponse response;
        try {
            userService.deleteByStatus(userId);
            response = ServerResponse.createBySuccessMessage("用户删除成功");
        }catch (Exception e){
            log.error("用户删除失败",e);
            response = ServerResponse.createByErrorMessage("用户删除失败:"+e.getMessage());
        }
        if(log.isDebugEnabled()){
            log.debug("删除用户ID为{}.", userId);
        }
        return response;
    }

    @PostMapping("/deleteBatch")
    @ApiOperation(value = "用户批量删除服务")
    public ServerResponse deleteBatch(String userIds) {
        ServerResponse response;
        try {
            userService.deleteBatchByStatus(userIds);
            response = ServerResponse.createBySuccessMessage("用户批量删除成功");
        }catch (Exception e){
            log.error("用户批量删除失败",e);
            response = ServerResponse.createByErrorMessage("用户批量删除失败:"+e.getMessage());
        }
        if(log.isDebugEnabled()){
            log.debug("批量删除用户ID为{}.", userIds);
        }
        return response;
    }

}
