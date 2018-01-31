package club.javalearn.crm.web.controller.security;

import club.javalearn.crm.common.ServerResponse;
import club.javalearn.crm.model.Resource;
import club.javalearn.crm.model.Role;
import club.javalearn.crm.service.ResourceService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-01-18
 **/
@Slf4j
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/page")
    public ModelAndView page(HttpServletRequest request){
        //项目名称
        String path = request.getContextPath();
        //绝对路径
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        System.out.println("请求绝对路径为:"+basePath);
        ModelAndView view = new ModelAndView("/security/resource/resource");
        view.addObject("baseUrl",basePath);
        return view;
    }


    @GetMapping
    @ApiOperation(value = "资源查询服务")
    public List<Resource> query() {
        String a = "";
        return resourceService.getList(a);
    }

    @PutMapping("/{resourceId:\\d+}")
    @ApiOperation(value = "资源修改服务")
    public ServerResponse update(@RequestBody Resource resource) {
        ServerResponse response;
        try {
            resourceService.update(resource);
            response = ServerResponse.createBySuccessMessage("资源修改成功");
        }catch (Exception e){
            log.error("资源修改失败",e);
            response = ServerResponse.createByErrorMessage("资源修改失败:"+e.getMessage());
        }
        if(log.isDebugEnabled()){
            log.debug("修改资源ID为{}.", resource.getResourceId());
            log.debug("修改资源为:{}", resource);
        }
        return response;
    }

    @PostMapping
    @ApiOperation(value = "资源创建服务")
    public ServerResponse create(@RequestBody Resource resource) {
        ServerResponse response;
        try {
            resourceService.create(resource);
            response = ServerResponse.createBySuccessMessage("角色资源成功");
        }catch (Exception e){
            log.error("角色修改失败",e);
            response = ServerResponse.createByErrorMessage("角色资源失败:"+e.getMessage());
        }
        if(log.isDebugEnabled()){
            log.debug("新增资源ID为{}.", resource.getResourceId());
            log.debug("新增资源为:{}", resource);
        }
        return response;
    }

    @GetMapping(value = "/select")
    @ApiOperation(value = "选择上级资源")
    public ServerResponse<List<Resource>> select() {
        ServerResponse<List<Resource>> response;
        try {
            response = ServerResponse.createBySuccess(resourceService.getList());
        }catch (Exception e){
            log.error("角色修改失败",e);
            response = ServerResponse.createByErrorMessage("角色修改失败:"+e.getMessage());
        }
        return response;
    }

    @GetMapping(value = "/{resourceId:\\d+}")
    @ApiOperation(value = "选择上级资源")
    public ServerResponse<Resource> getInfo(@PathVariable("resourceId") String resourceId) {
        ServerResponse<Resource> response;
        try {
            response = ServerResponse.createBySuccess(resourceService.selectOne(resourceId));
        }catch (Exception e){
            log.error("角色修改失败",e);
            response = ServerResponse.createByErrorMessage("角色修改失败:"+e.getMessage());
        }
        return response;
    }

}
