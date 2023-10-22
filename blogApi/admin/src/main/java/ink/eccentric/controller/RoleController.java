package ink.eccentric.controller;

import ink.eccentric.annotation.SystemLog;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.service.RoleService;
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
@RequestMapping("/role")
@Api(tags = "角色",description = "角色相关接口")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 获取所有角色列表
     * @return
     */
    @GetMapping("/listAllRole")
    @SystemLog(businessName = "获取所有角色列表")
    @ApiOperation(value = "获取所有角色列表",notes = "获取所有角色列表")
    public Result getAllRoles(){
        return roleService.getAllRoles();
    }
}
