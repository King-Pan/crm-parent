package club.javalearn.crm.web.controller.security;

import club.javalearn.crm.model.Resource;
import club.javalearn.crm.service.ResourceService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView page(){
        return new ModelAndView("/security/resource/resource");
    }


    @GetMapping
    @ApiOperation(value = "资源查询服务")
    public List<Resource> query() {
        return resourceService.getList();
    }

}
