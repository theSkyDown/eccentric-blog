package ink.eccentric.controller;

import ink.eccentric.annotation.SystemLog;
import ink.eccentric.constants.SystemConstants;
import ink.eccentric.domain.dto.menu.MenuDto;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.enums.AppHttpCodeEnum;
import ink.eccentric.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


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


    /**
     * 获取菜单列表
     * @param status
     * @param menuName
     * @return
     */
    @GetMapping("/list")
    @SystemLog(businessName = "获取菜单列表")
    @ApiOperation(value = "获取菜单列表",notes = "获取菜单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status",value = "状态"),
            @ApiImplicitParam(name = "menuName",value = "菜单名")
    })
    public Result list(String status,String menuName){
        return menuService.list(status,menuName);
    }

    /**
     * 添加菜单
     * @return
     */
    @PostMapping("/add")
    @SystemLog(businessName = "添加菜单")
    @ApiOperation(value = "添加菜单",notes = "添加菜单")
    public Result addMenu(@RequestBody MenuDto menuDto){
        Result result = checkParams(menuDto,false);
        if (Objects.nonNull(result)){
            return result;
        }
        return menuService.addMenu(menuDto);
    }

    @DeleteMapping("/delete/{id}")
    @SystemLog(businessName = "删除菜单")
    @ApiOperation(value = "删除菜单",notes = "删除菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "要删除的菜单id")
    })
    public Result deleteMenu(@PathVariable Long id){
        if (notOk(id)) {
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        return menuService.deleteMenu(id);
    }


    @GetMapping("/{id}")
    @SystemLog(businessName = "获取菜单详细信息")
    @ApiOperation(value = "获取菜单详细信息",notes = "获取菜单详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "要查询的详细信息")
    })
    public Result getMenuDetail(@PathVariable Long id){
        if (notOk(id)) {
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        return menuService.getMenuDetail(id);
    }

    @PutMapping("/update")
    @SystemLog(businessName = "修改菜单")
    @ApiOperation(value = "修改菜单",notes = "修改菜单")
    public Result updateMenu(@RequestBody MenuDto menuDto){
        Result result = checkParams(menuDto,true);
        if (Objects.nonNull(result)){
            return result;
        }
        return menuService.updateMenu(menuDto);
    }

    /**
     * 检测参数是否正常
     * @param menuDto
     * @return
     */
    private Result checkParams(MenuDto menuDto,Boolean isUpdate){
        if (notOk(menuDto)){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        if (isUpdate && notOk(menuDto.getId())){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        if (isUpdate && menuDto.getId().equals(menuDto.getParentId())){
            return Result.fail().message("上级菜单不能选择自己");
        }
        if (notOk(menuDto.getMenuType())){
            return Result.fail().message("请选择菜单的类型");
        }
        if (notOk(menuDto.getStatus())){
            return Result.fail().message("请选择菜单的状态");
        }
        if (notOk(menuDto.getVisible())){
            return Result.fail().message("请选择菜单的显示状态");
        }
        if (notOk(menuDto.getMenuName())){
            return Result.fail().message("请填写菜单名称");
        }
        if (notOk(menuDto.getOrderNum())){
            return Result.fail().message("请填写菜单的显示排序");
        }
        List<String> list = List.of(SystemConstants.DIRECTORY,SystemConstants.MENU);
        if (list.contains(menuDto.getMenuType()) && notOk(menuDto.getPath())){
            return Result.fail().message("请选择菜单的路由地址");
        }
        return null;
    }

}
