package ink.eccentric.controller;

import ink.eccentric.annotation.SystemLog;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author eccentric
 */
@RestController
@RequestMapping("/menu")
@Api(tags = "菜单",description = "菜单相关接口")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    /**
     * 获取菜单信息
     * @return
     */
    @GetMapping("/getRouters")
    @SystemLog(businessName = "获取菜单信息")
    @ApiOperation(value = "获取菜单信息",notes = "获取菜单信息")
    public Result getRoutes(){
        return menuService.getRoutes();
    }


    /**
     * 获取树形菜选项列表
     * @return
     */
    @GetMapping("/treeSelect")
    @SystemLog(businessName = "获取树形菜选项列表")
    @ApiOperation(value = "获取树形菜选项列表",notes = "获取树形菜选项列表")
    public Result getTreeSelect(){
        return menuService.getTreeSelect();
    }

}
