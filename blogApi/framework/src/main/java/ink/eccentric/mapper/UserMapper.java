package ink.eccentric.mapper;

import ink.eccentric.domain.po.User;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.domain.vo.user.UpdateUserInfoVo;
import ink.eccentric.domain.vo.user.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author eccentric
 */
@Mapper
public interface UserMapper {
    /**
     * 通过用户名查找用户信息
     * @param username
     * @return
     */
    public User getUserByUsername(@Param("username") String username);

    /**
     * 查找出用户所拥有的权限
     * @param id
     * @return
     */
    public List<String> getUserPermissions(@Param("id") Long id);


    /**
     * 修改用户的信息
     * @param user
     * @return
     */
    public Integer updateUserInfo(User user);

    /**
     * 通过昵称获取用户信息
     * @param nickName
     * @return
     */
    public User getUserByNickName(@Param("nickName") String nickName);


    /**
     * 通过邮箱获取用户信息
     * @param email
     * @return
     */
    public User getUserByEmail(@Param("email") String email);

    /**
     * 插入(添加)用户信息
     * @param user
     * @return
     */
    public Integer insertUser(User user);

    /**
     * 分页查询用户列表
     * @param pageNum
     * @param pageSize
     * @param username
     * @param phone
     * @param status
     * @return
     */
    public List<UserVo> getUserPagesList(@Param("pageNum") Integer pageNum,@Param("pageSize") Integer pageSize,@Param("username") String username,@Param("phone") String phone,@Param("status") String status);

    /**
     * 查询用户的数量
     * @param username
     * @param phone
     * @param status
     * @return
     */
    public Integer getUserCount(@Param("username") String username,@Param("phone") String phone,@Param("status") String status);

    /**
     * 删除用户
     * @param ids
     * @param updateBy
     * @param updateTime
     * @return
     */
    public Integer deleteUser(@Param("ids") List<Long> ids, @Param("updateBy") Long updateBy,@Param("updateTime") Date updateTime);

    /**
     * 获取用户详细信息
     * @param id
     * @return
     */
    public UpdateUserInfoVo getUserDetailById(@Param("id") Long id);

    /**
     * 修改用户
     * @param user
     * @return
     */
    public Integer updateUser(User user);

    /**
     * 修改用户的状态
     * @param user
     * @return
     */
    public Integer changeStatus(User user);
}
