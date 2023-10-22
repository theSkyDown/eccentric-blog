package ink.eccentric.service;

import ink.eccentric.domain.dto.user.*;
import ink.eccentric.domain.po.User;
import ink.eccentric.domain.vo.common.Result;

import java.util.List;

/**
 * @author eccentric
 */
public interface UserService {
    /**
     * 用户登陆
     * @param user
     * @return
     */
    public Result login(UserLoginDto user);

    /**
     * 用户推出登陆
     * @return
     */
    public Result logout();

    /**
     * 获取用户的详细信息
     * @return
     */
    public Result getUserInfo();

    /**
     * 修改用户的个人信息
     * @param userInfoDto
     * @return
     */
    public Result updateUserInfo(UpdateUserInfoDto userInfoDto);

    /**
     * 注册用户
     * @param registerDto
     * @return
     */
    public Result register(RegisterDto registerDto);

    /**
     * 后台用户登陆
     * @param user
     * @return
     */
    public Result adminLogin(UserLoginDto user);

    /**
     * 后台用户退出登陆
     * @return
     */
    public Result adminLogout();

    /**
     * 管理系统获取用户的详细信息
     * @return
     */
    public Result adminGetUserInfo();

    /**
     * 分页获取用户列表
     * @param pageNum
     * @param pageSize
     * @param username
     * @param phone
     * @param status
     * @return
     */
    public Result getUserPagesList(Integer pageNum, Integer pageSize, String username, String phone, String status);

    /**
     * 添加用户
     * @param addUserDto
     * @return
     */
    public Result addUser(AddUserDto addUserDto);

    /**
     * 删除用户
     * @param idList
     * @return
     */
    public Result deleteUser(List<Long> idList);

    /**
     * 获取用户的详细信息
     * @param id
     * @return
     */
    public Result getUserDetail(Long id);

    /**
     * 修改用户
     * @param updateUserDto
     * @return
     */
    public Result updateUser(UpdateUserDto updateUserDto);

    /**
     * 修改用户的状态
     * @param updateUserStatusDto
     * @return
     */
    public Result changeStatus(UpdateUserStatusDto updateUserStatusDto);
}
