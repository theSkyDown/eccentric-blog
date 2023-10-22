package ink.eccentric.controller;

import ink.eccentric.annotation.SystemLog;
import ink.eccentric.domain.dto.user.AddUserDto;
import ink.eccentric.domain.dto.user.UpdateUserDto;
import ink.eccentric.domain.dto.user.UpdateUserStatusDto;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.domain.dto.user.UserLoginDto;
import ink.eccentric.enums.AppHttpCodeEnum;
import ink.eccentric.exception.SystemException;
import ink.eccentric.service.UserService;
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
@RequestMapping("/user")
@Api(tags = "用户",description = "用户相关接口")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    /**
     * 用户登陆
     * @param user
     * @return
     */
    @PostMapping("/login")
    @SystemLog(businessName = "用户登陆")
    @ApiOperation(value = "用户登陆",notes = "用户登陆")
    public Result login(@RequestBody UserLoginDto user){
        if (notOk(user.getUsername()) || notOk(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PARAMS_FAIL);
        }
        return userService.adminLogin(user);
    }


    /**
     * 用户退出登陆
     * @return
     */
    @PostMapping("/logout")
    @SystemLog(businessName = "用户退出")
    @ApiOperation(value = "用户退出",notes = "用户退出")
    public Result logout(){
        return userService.adminLogout();
    }


    /**
     * 获取用户的详细信息
     * @return
     */
    @GetMapping("/getInfo")
    @SystemLog(businessName = "获取用户的详细信息")
    @ApiOperation(value = "获取用户的详细信息",notes = "获取用户的详细信息")
    public Result getInfo(){
        return userService.adminGetUserInfo();
    }


    /**
     * 分页获取用户列表
     * @param pageNum
     * @param pageSize
     * @param username
     * @param phone
     * @param status
     * @return
     */
    @GetMapping("/list")
    @SystemLog(businessName = "分页获取用户列表")
    @ApiOperation(value = "分页获取用户列表",notes = "分页获取用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "分页参数"),
            @ApiImplicitParam(name = "pageSize",value = "分页参数"),
            @ApiImplicitParam(name = "username",value = "用户名"),
            @ApiImplicitParam(name = "phone",value = "电话号码"),
            @ApiImplicitParam(name = "status",value = "状态")
    })
    public Result getUserPagesList(@RequestParam Integer pageNum,@RequestParam Integer pageSize,String username,String phone,String status){
        //拦截不正常参数
        if (notOk(pageNum) || notOk(pageNum)){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        return userService.getUserPagesList(pageNum,pageSize,username,phone,status);
    }

    /**
     * 添加用户
     * @return
     */
    @PostMapping("/add")
    @SystemLog(businessName = "添加用户")
    @ApiOperation(value = "添加用户",notes = "添加用户")
    public Result addUser(@RequestBody AddUserDto addUserDto){
        Result result = checkParams(addUserDto);
        if (Objects.nonNull(result)){
            return result;
        }
        return userService.addUser(addUserDto);
    }

    /**
     * 删除用户
     * @param ids
     * @return
     */
    @DeleteMapping("/delete/{ids}")
    @SystemLog(businessName = "删除用户(支持批量)")
    @ApiOperation(value = "删除用户(支持批量)",notes = "删除用户(支持批量)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "批量删除的id"),
    })
    public Result deleteUser(@PathVariable String ids){
        List<Long> idList = null;
        try {
            idList = ParamsTransformationUtil.idsToList(ids);
        } catch (SystemException e) {
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(), AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        if (idList.size() < 1) {
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(), AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        return userService.deleteUser(idList);
    }

    /**
     * 获取用户的详细信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @SystemLog(businessName = "获取用户的详细信息")
    @ApiOperation(value = "获取用户的详细信息",notes = "获取用户的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "查询的id")
    })
    public Result getUserDetail(@PathVariable Long id){
        if (notOk(id)){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(), AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        return userService.getUserDetail(id);
    }

    /**
     * 修改用户
     * @param updateUserDto
     * @return
     */
    @PutMapping("/update")
    @SystemLog(businessName = "修改用户")
    @ApiOperation(value = "修改用户",notes = "修改用户")
    public Result updateUser(@RequestBody UpdateUserDto updateUserDto){
        Result result = checkParams(updateUserDto);
        if (Objects.nonNull(result)){
            return result;
        }
        return userService.updateUser(updateUserDto);
    }


    @PutMapping("/changeStatus")
    @SystemLog(businessName = "修改用户状态")
    @ApiOperation(value = "修改用户状态",notes = "修改用户状态")
    public Result changeStatus(@RequestBody UpdateUserStatusDto updateUserStatusDto){
        if (notOk(updateUserStatusDto.getId())){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        if (notOk(updateUserStatusDto.getStatus())){
            return Result.fail().message("请选择状态");
        }
        return userService.changeStatus(updateUserStatusDto);
    }

    private Result checkParams(AddUserDto addUserDto){
        if (notOk(addUserDto)){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        if (notOk(addUserDto.getNickName())){
            return Result.fail().message("用户昵称不能为空");
        }
        if (notOk(addUserDto.getUserName())){
            return Result.fail().message("用户名不能为空");
        }
        if (notOk(addUserDto.getPassword())){
            return Result.fail().message("密码不能为空");
        }
        if (notOk(addUserDto.getStatus())){
            return Result.fail().message("用户的状态不能为空");
        }
        if (notOk(addUserDto.getRoleIds())){
            return Result.fail().message("请选择用户的角色");
        }
        return null;
    }

    private Result checkParams(UpdateUserDto updateUserDto){
        if (notOk(updateUserDto)){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        if (notOk(updateUserDto.getNickName())){
            return Result.fail().message("用户昵称不能为空");
        }
        if (notOk(updateUserDto.getStatus())){
            return Result.fail().message("用户的状态不能为空");
        }
        if (notOk(updateUserDto.getRoleIds())){
            return Result.fail().message("请选择用户的角色");
        }
        return null;
    }
}
