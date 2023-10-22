package ink.eccentric.service.impl;

import com.alibaba.fastjson2.JSON;
import ink.eccentric.constants.SystemConstants;
import ink.eccentric.domain.dto.user.*;
import ink.eccentric.domain.po.Article;
import ink.eccentric.domain.po.Category;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.domain.vo.common.UserDetailsImpl;
import ink.eccentric.domain.po.User;
import ink.eccentric.domain.vo.role.RoleVo;
import ink.eccentric.domain.vo.user.*;
import ink.eccentric.enums.AppHttpCodeEnum;
import ink.eccentric.exception.SystemException;
import ink.eccentric.mapper.UserMapper;
import ink.eccentric.service.RoleService;
import ink.eccentric.service.UserRoleService;
import ink.eccentric.service.UserService;
import ink.eccentric.utils.BeanCopyUtil;
import ink.eccentric.utils.JwtUtil;
import ink.eccentric.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.Duration;
import java.util.*;

/**
 * @author eccentric
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;


    @Override
    public Result login(UserLoginDto user) {
        //身份认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //认证失败
        if (Objects.isNull(authenticate)){
            throw new SystemException(AppHttpCodeEnum.AUTHENTICATE_FAIL);
        }
        //认证通过
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
        //将信息存储至redis，并设置过期时间
        User loginUser = userDetails.getUser();
        //用户被禁用
        if (!SystemConstants.NORMAL_STATUS.equals(loginUser.getStatus())){
            return Result.fail().message("用户已被禁用");
        }
        Long id = loginUser.getId();
        redisTemplate.opsForValue().set("BlogLogin:"+id, JSON.toJSONString(userDetails),Duration.ofMillis(JwtUtil.TIME));
        //生成jwt
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",id);
        String jwt = JwtUtil.createJwt("BlogLogin",claims);
        //封装成vo，然后在封装成Result返回
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setToken(jwt);
        loginUserVo.setUserInfo(BeanCopyUtil.copyBean(loginUser, UserInfoVo.class));
        return Result.ok(loginUserVo);
    }


    @Override
    public Result logout() {
        //获取当前登陆用户的信息
        Long userid = SecurityUtil.getUser().getId();
        redisTemplate.delete("BlogLogin:"+userid);
        return Result.ok();
    }

    @Override
    public Result getUserInfo() {
        return Result.ok(BeanCopyUtil.copyBean(SecurityUtil.getUser(), UserInfoVo.class));
    }

    @Override
    public Result updateUserInfo(UpdateUserInfoDto userInfoDto) {
        //获取当前用户的id
        Long userId = SecurityUtil.getUser().getId();
        userInfoDto.setId(userId);
        //修改用户信息
        User user = BeanCopyUtil.copyBean(userInfoDto, User.class);
        //设置修改人id
        user.setUpdateTime(new Date());
        user.setUpdateBy(SecurityUtil.getUser().getId());
        return userMapper.updateUserInfo(user)==1?Result.ok():Result.fail();
    }

    @Override
    @Transactional
    public Result register(RegisterDto registerDto) {
        Result result = checkInfo(registerDto);
        if (Objects.nonNull(result)){
            return result;
        }
        //对密码进行加密
        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        //没有重复数据则创建用户
        User user = BeanCopyUtil.copyBean(registerDto, User.class);
        //设置创建人
        user.setCreateBy(-1L);
        //设置创建时间
        user.setCreateTime(new Date());
        //设置更新人
        user.setUpdateBy(-1L);
        //设置更新时间
        user.setUpdateTime(new Date());
        //设置类型（是否为管理员）
        user.setType(SystemConstants.NOT_ADMIN);
        Integer addUserCount = userMapper.insertUser(user);
        //如果没添加成功则回滚
        if (addUserCount < 1){
            //执行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail();
        }
        Integer count = userRoleService.addUserRole(user.getId(),List.of(SystemConstants.NORMAL_ROLE_ID));
        if (count < 1) {
            //执行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail();
        }
        return Result.ok();
    }

    @Override
    public Result adminLogin(UserLoginDto user) {
        //身份认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //认证失败
        if (Objects.isNull(authenticate)){
//            throw new SystemException(AppHttpCodeEnum.AUTHENTICATE_FAIL);
            return Result.fail(AppHttpCodeEnum.AUTHENTICATE_FAIL.getCode(),AppHttpCodeEnum.AUTHENTICATE_FAIL.getMsg());
        }
        //认证通过
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
        //将信息存储至redis，并设置过期时间
        User loginUser = userDetails.getUser();
        //若用户不是管理员则认证失败
        if (!SystemConstants.IS_ADMIN.equals(loginUser.getType())){
            return Result.fail().message("用户不是管理员，认证失败");
        }
        //若用户已被禁用
        if (!SystemConstants.NORMAL_STATUS.equals(loginUser.getStatus())){
            return Result.fail().message("用户已被禁用");
        }
        Long id = loginUser.getId();
        redisTemplate.opsForValue().set("AdminLogin:"+id, JSON.toJSONString(userDetails),Duration.ofMillis(JwtUtil.TIME));
        //生成jwt
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",id);
        String jwt = JwtUtil.createJwt("AdminLogin",claims);
        //封装成vo，然后在封装成Result返回
        AdminLoginUserVo loginUserVo = new AdminLoginUserVo();
        loginUserVo.setToken(jwt);
        return Result.ok(loginUserVo);
    }

    @Override
    public Result adminLogout() {
        //获取当前登陆用户的信息
        Long userid = SecurityUtil.getUser().getId();
        redisTemplate.delete("AdminLogin:"+userid);
        return Result.ok();
    }

    @Override
    public Result adminGetUserInfo() {
        User user = SecurityUtil.getUser();
        if (Objects.isNull(user) || Objects.isNull(user.getId())){
            return Result.fail().message("用户信息异常");
        }
        //获取当前登陆用户的id
        AdminUserInfoVo userInfoVo = new AdminUserInfoVo();
        //获取该用户的权限信息
        userInfoVo.setPermissions(SecurityUtil.getPermissionList());
        //获取该用户的角色信息
        userInfoVo.setRoles(roleService.getRolesKeyByUserId(user.getId()));
        //获取该用户的其他信息
        userInfoVo.setUser(BeanCopyUtil.copyBean(user,UserInfoVo.class));
        return Result.ok(userInfoVo);
    }

    @Override
    public Result getUserPagesList(Integer pageNum, Integer pageSize, String username, String phone, String status) {
        Map<String,Object> res = new HashMap<>();
        List<UserVo> userList = userMapper.getUserPagesList(pageNum-1,pageSize,username,phone,status);
        res.put("rows",userList);
        Integer count = userMapper.getUserCount(username,phone,status);
        res.put("total",count);
        return Result.ok(res);
    }

    @Override
    @Transactional
    public Result addUser(AddUserDto addUserDto) {
        Result result = checkInfo(addUserDto);
        if (Objects.nonNull(result)){
            return result;
        }
        User user = BeanCopyUtil.copyBean(addUserDto, User.class);
        //密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //设置一些其他信息，如创建时间、创建人、更新时间、更新人
        //创建人
        user.setCreateBy(SecurityUtil.getUser().getId());
        //更新人
        user.setUpdateBy(SecurityUtil.getUser().getId());
        //创建时间
        user.setCreateTime(new Date());
        //更新时间
        user.setUpdateTime(new Date());
        //设置类型（是否为管理）
        user.setType(SystemConstants.IS_ADMIN);
        Integer addUserCount = userMapper.insertUser(user);
        //如果没添加成功则回滚
        if (addUserCount < 1){
            //执行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail();
        }
        Integer count = userRoleService.addUserRole(user.getId(),addUserDto.getRoleIds());
        if (count < 1) {
            //执行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail();
        }
        return Result.ok();
    }

    @Override
    public Result deleteUser(List<Long> idList) {
        if (idList.contains(SystemConstants.ADMIN_ID)){
            return Result.fail().message("无法删除超级管理员");
        }
        return userMapper.deleteUser(idList,SecurityUtil.getUser().getId(),new Date())>0?Result.ok():Result.fail();
    }

    @Override
    public Result getUserDetail(Long id) {
        Map<String,Object> res = new HashMap<>();
        //获取所有的角色信息
        Result allRolesResult = roleService.getAllRoles();
        Object roles = allRolesResult.getData();
        res.put("roles",roles);
        //获取用户的信息
        UpdateUserInfoVo user = userMapper.getUserDetailById(id);
        //获取该用户的角色id列表
        List<Long> roleIds = userRoleService.getRoleIdsByUserId(id);
        user.setRoleIds(roleIds);
        res.put("user",user);
        return Result.ok(res);
    }

    @Override
    @Transactional
    public Result updateUser(UpdateUserDto updateUserDto) {
        Result result = checkInfo(updateUserDto);
        if (Objects.nonNull(result)){
            return result;
        }
        //需开启事务，先修改用户再重新添加对应的角色至sys_user_role表
        User user = BeanCopyUtil.copyBean(updateUserDto, User.class);
        //更新人
        user.setUpdateBy(SecurityUtil.getUser().getId());
        //更新时间
        user.setUpdateTime(new Date());
        Integer updateUserCount = userMapper.updateUser(user);
        //如果没修改成功，则回滚
        if (updateUserCount<1){
            //执行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail();
        }
        //删除该用户所有的角色
        Integer deleteAllRolesCount = userRoleService.deleteAllRolesByUserId(user.getId());
        if (deleteAllRolesCount < 1){
            //执行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail();
        }
        //重新添加对应的角色
        Integer addUserRoleCount = userRoleService.addUserRole(user.getId(),updateUserDto.getRoleIds());
        if (addUserRoleCount < 1) {
            //执行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail();
        }
        return Result.ok();
    }

    @Override
    public Result changeStatus(UpdateUserStatusDto updateUserStatusDto) {
        if (updateUserStatusDto.getId().equals(SystemConstants.ADMIN_ID)){
            return Result.fail().message("无法对超级管理员进行操作");
        }
        User user = BeanCopyUtil.copyBean(updateUserStatusDto, User.class);
        //更新人
        user.setUpdateBy(SecurityUtil.getUser().getId());
        //更新时间
        user.setUpdateTime(new Date());
        return userMapper.changeStatus(user)==1?Result.ok():Result.fail();
    }

    /**
     * 检测参数是否已存在
     * @param addUserDto
     * @return
     */
    private Result checkInfo(AddUserDto addUserDto){
        if (Objects.nonNull(userMapper.getUserByUsername(addUserDto.getUserName()))){
            return Result.fail().message("用户名已存在");
        }
        if (Objects.nonNull(userMapper.getUserByEmail(addUserDto.getEmail()))){
            return Result.fail().message("邮箱已存在");
        }
        if (Objects.nonNull(userMapper.getUserByNickName(addUserDto.getNickName()))){
            return Result.fail().message("昵称已存在");
        }
        return null;
    }
    /**
     * 检测参数是否已存在
     * @param registerDto
     * @return
     */
    private Result checkInfo(RegisterDto registerDto){
        if (Objects.nonNull(userMapper.getUserByUsername(registerDto.getUserName()))){
            return Result.fail().message("用户名已存在");
        }
        if (Objects.nonNull(userMapper.getUserByEmail(registerDto.getEmail()))){
            return Result.fail().message("邮箱已存在");
        }
        if (Objects.nonNull(userMapper.getUserByNickName(registerDto.getNickName()))){
            return Result.fail().message("昵称已存在");
        }
        return null;
    }

    /**
     * 检测参数是否已存在
     * @param updateUserDto
     * @return
     */
    private Result checkInfo(UpdateUserDto updateUserDto){
        User userByEmail = userMapper.getUserByEmail(updateUserDto.getEmail());
        if (Objects.nonNull(userByEmail) && !updateUserDto.getId().equals(userByEmail.getId())){
            return Result.fail().message("邮箱已存在");
        }
        User userByNickName = userMapper.getUserByNickName(updateUserDto.getNickName());
        if (Objects.nonNull(userByNickName) && !updateUserDto.getId().equals(userByNickName.getId())){
            return Result.fail().message("昵称已存在");
        }
        return null;
    }
}
