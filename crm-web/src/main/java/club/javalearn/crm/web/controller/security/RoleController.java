package club.javalearn.crm.web.controller.security;

import club.javalearn.crm.common.ServerResponse;
import club.javalearn.crm.model.Role;
import club.javalearn.crm.service.RoleService;
import club.javalearn.crm.utils.Message;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-01-16
 **/
@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/page")
    public ModelAndView page(){
        return new ModelAndView("security/role/role");
    }

    /**
     *
     * @param param 查询参数
     * @param pageable 分页参数
     * @return 封装的用户分页数据
     */
    @GetMapping
    @ApiOperation(value = "角色查询服务")
    public Message<Role> query(@RequestParam(name = "param",required = false) String  param, @PageableDefault Pageable pageable) {
        log.debug("查询参数:"+param);
        return roleService.getList(param,pageable);
    }

    @PutMapping("/{roleId:\\d+}")
    @ApiOperation(value = "角色修改服务")
    public ServerResponse update(@RequestBody Role role) {
        ServerResponse response;
        try {
            roleService.update(role);
            response = ServerResponse.createBySuccessMessage("角色修改成功");
        }catch (Exception e){
            log.error("角色修改失败",e);
            response = ServerResponse.createByErrorMessage("角色修改失败:"+e.getMessage());
        }
        if(log.isDebugEnabled()){
            log.debug("修改角色ID为{}.", role.getRoleId());
            log.debug("修改角色户为:{}", role);
        }
        return response;
    }

    @PostMapping
    @ApiOperation(value = "角色创建服务")
    public ServerResponse create(@RequestBody Role role) {
        ServerResponse response;
        try {
            roleService.create(role);
            response = ServerResponse.createBySuccessMessage("角色创建成功");
        }catch (Exception e){
            log.error("角色修改失败",e);
            response = ServerResponse.createByErrorMessage("角色创建失败:"+e.getMessage());
        }
        if(log.isDebugEnabled()){
            log.debug("新增角色ID为{}.", role.getRoleId());
            log.debug("新增角色为:{}", role);
        }
        return response;
    }

    @DeleteMapping("/{roleId:\\d+}")
    @ApiOperation(value = "角色删除服务")
    public ServerResponse delete(@PathVariable("roleId")Long roleId) {
        ServerResponse response;
        try {

            roleService.deleteByStatus(roleId);
            response = ServerResponse.createBySuccessMessage("角色删除成功");
        }catch (Exception e){
            log.error("角色删除失败",e);
            response = ServerResponse.createByErrorMessage("角色删除失败:"+e.getMessage());
        }
        if(log.isDebugEnabled()){
            log.debug("删除角色ID为{}.", roleId);
        }
        return response;
    }

    @PostMapping("/deleteBatch")
    @ApiOperation(value = "角色批量删除服务")
    public ServerResponse deleteBatch(String roleIds) {
        ServerResponse response;
        try {
            roleService.deleteBatchByStatus(roleIds);
            response = ServerResponse.createBySuccessMessage("角色批量删除成功");
        }catch (Exception e){
            log.error("角色批量删除失败",e);
            response = ServerResponse.createByErrorMessage("角色批量删除失败:"+e.getMessage());
        }
        if(log.isDebugEnabled()){
            log.debug("批量删除角色ID为{}.", roleIds);
        }
        return response;
    }
}
