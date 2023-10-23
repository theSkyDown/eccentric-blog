package ink.eccentric.controller;

import ink.eccentric.annotation.SystemLog;
import ink.eccentric.domain.dto.role.AddRoleDto;
import ink.eccentric.domain.dto.role.UpdateRoleDto;
import ink.eccentric.domain.dto.role.UpdateRoleStatusDto;
import ink.eccentric.domain.dto.user.UpdateUserStatusDto;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.enums.AppHttpCodeEnum;
import ink.eccentric.exception.SystemException;
import ink.eccentric.service.RoleService;
import ink.eccentric.utils.ParamsTransformationUtil;
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

    /**
     * 分页获取角色列表
     * @param pageNum
     * @param pageSize
     * @param roleName
     * @param status
     * @return
     */
    @GetMapping("/list")
    @SystemLog(businessName = "分页获取角色列表")
    @ApiOperation(value = "分页获取角色列表",notes = "分页获取角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "分页参数"),
            @ApiImplicitParam(name = "pageSize",value = "分页参数"),
            @ApiImplicitParam(name = "roleName",value = "角色名称"),
            @ApiImplicitParam(name = "status",value = "状态")
    })
    public Result list(@RequestParam Integer pageNum,@RequestParam Integer pageSize,String roleName,String status){
        //拦截不正常参数
        if (notOk(pageNum) || notOk(pageNum)){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        return roleService.getRolesPageList(pageNum,pageSize,roleName,status);
    }

    /**
     * 添加角色
     * @return
     */
    @PostMapping("/add")
    @SystemLog(businessName = "添加角色")
    @ApiOperation(value = "添加角色",notes = "添加角色")
    public Result addRole(@RequestBody AddRoleDto addRoleDto){
        Result result = checkParams(addRoleDto);
        if (Objects.nonNull(result)){
            return result;
        }
        return roleService.addRole(addRoleDto);
    }

    /**
     * 删除角色
     * @param ids
     * @return
     */
    @DeleteMapping("/delete/{ids}")
    @SystemLog(businessName = "删除角色(支持批量)")
    @ApiOperation(value = "删除角色(支持批量)",notes = "删除角色(支持批量)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "批量删除的id")
    })
    public Result deleteRole(@PathVariable String ids){
        List<Long> idList = null;
        try {
            idList = ParamsTransformationUtil.idsToList(ids);
        } catch (SystemException e) {
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(), AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        if (idList.size() < 1) {
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(), AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        return roleService.deleteRole(idList);
    }

    /**
     * 获取角色的详细信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @SystemLog(businessName = "获取角色的详细信息")
    @ApiOperation(value = "获取角色的详细信息",notes = "获取角色的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "查询的id")
    })
    public Result getRoleDetail(@PathVariable Long id){
        if (notOk(id)){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(), AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        return roleService.getRoleDetail(id);
    }


    /**
     * 修改角色
     * @param updateRoleDto
     * @return
     */
    @PutMapping("/update")
    @SystemLog(businessName = "修改角色")
    @ApiOperation(value = "修改角色",notes = "修改角色")
    public Result updateRole(@RequestBody UpdateRoleDto updateRoleDto){
        Result result = checkParams(updateRoleDto);
        if (Objects.nonNull(result)){
            return result;
        }
        return roleService.updateRole(updateRoleDto);
    }

    /**
     * 修改角色状态
     * @param updateRoleStatusDto
     * @return
     */
    @PutMapping("/changeStatus")
    @SystemLog(businessName = "修改角色状态")
    @ApiOperation(value = "修改角色状态",notes = "修改角色状态")
    public Result changeStatus(@RequestBody UpdateRoleStatusDto updateRoleStatusDto){
        if (notOk(updateRoleStatusDto.getId())){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        if (notOk(updateRoleStatusDto.getStatus())){
            return Result.fail().message("请选择状态");
        }
        return roleService.changeStatus(updateRoleStatusDto);
    }


    /**
     * 检测参数是否异常
     * @param addRoleDto
     * @return
     */
    private Result checkParams(AddRoleDto addRoleDto){
        if (notOk(addRoleDto)){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        if (notOk(addRoleDto.getRoleName())){
            return Result.fail().message("请填写角色名称");
        }
        if (notOk(addRoleDto.getRoleKey())){
            return Result.fail().message("请填写权限字符");
        }
        if (notOk(addRoleDto.getRoleSort())){
            return Result.fail().message("请填写角色顺序");
        }
        return null;
    }

    /**
     * 检测参数是否异常
     * @param updateRoleDto
     * @return
     */
    private Result checkParams(UpdateRoleDto updateRoleDto){
        if (notOk(updateRoleDto)){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        if (notOk(updateRoleDto.getId())){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        if (notOk(updateRoleDto.getRoleKey())){
            return Result.fail().message("请填写权限字符");
        }
        if (notOk(updateRoleDto.getRoleSort())){
            return Result.fail().message("请填写角色顺序");
        }
        return null;
    }


}
