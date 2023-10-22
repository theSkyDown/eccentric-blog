package ink.eccentric.controller;

import ink.eccentric.annotation.SystemLog;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.domain.dto.user.RegisterDto;
import ink.eccentric.domain.dto.user.UpdateUserInfoDto;
import ink.eccentric.domain.dto.user.UserLoginDto;
import ink.eccentric.enums.AppHttpCodeEnum;
import ink.eccentric.exception.SystemException;
import ink.eccentric.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author eccentric
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户",description = "用户相关接口")
public class UserController extends BaseController {

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
            return Result.fail().message("请输入后在提交");
        }
        return userService.login(user);
    }

    /**
     * 用户退出
     * @return
     */
    @PostMapping("/logout")
    @SystemLog(businessName = "用户退出")
    @ApiOperation(value = "用户退出",notes = "用户退出")
    public Result logout(){
        return userService.logout();
    }

    /**
     * 获取当前用户的详细信息
     * @return
     */
    @GetMapping("/userInfo")
    @SystemLog(businessName = "获取当前用户的详细信息")
    @ApiOperation(value = "获取当前用户的详细信息",notes = "获取当前用户的详细信息")
    public Result userInfo(){
        return userService.getUserInfo();
    }

    /**
     * 修改用户的详细信息
     * @return
     */
    @PutMapping("/userInfo")
    @SystemLog(businessName = "修改用户的详细信息")
    @ApiOperation(value = "修改用户的详细信息",notes = "修改用户的详细信息")
    public Result updateUserInfo(@RequestBody UpdateUserInfoDto userInfoDto){
        if (notOk(userInfoDto)||
                notOk(userInfoDto.getNickName())||
                notOk(userInfoDto.getEmail())||
                notOk(userInfoDto.getSex())
        ){
            throw new SystemException(AppHttpCodeEnum.PARAMS_FAIL);
        }
        return userService.updateUserInfo(userInfoDto);
    }

    /**
     * 注册
     * @param registerDto
     * @return
     */
    @PostMapping("/register")
    @SystemLog(businessName = "注册")
    @ApiOperation(value = "注册",notes = "注册")
    public Result register(@RequestBody RegisterDto registerDto){
        System.out.println(registerDto);
        if (notOk(registerDto)||
                notOk(registerDto.getEmail())||
                notOk(registerDto.getNickName())||
                notOk(registerDto.getUserName())||
                notOk(registerDto.getPassword())
        ){
            throw new SystemException(AppHttpCodeEnum.PARAMS_FAIL);
        }
        return userService.register(registerDto);
    }
}
