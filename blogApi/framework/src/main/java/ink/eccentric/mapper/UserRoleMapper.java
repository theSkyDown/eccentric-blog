package ink.eccentric.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRoleMapper {
    /**
     * 添加用户角色
     * @param userId
     * @param roleIds
     * @return
     */
    public Integer addUserRole(@Param("userId") Long userId,@Param("roleIds") List<Long> roleIds);

    /**
     * 获取用户的角色id列表
     * @param userId
     * @return
     */
    public List<Long> getRoleIdsByUserId(@Param("userId") Long userId);

    /**
     * 删除用户的所有角色
     * @param userId
     * @return
     */
    public Integer deleteAllRolesByUserId(@Param("userId") Long userId);
}
