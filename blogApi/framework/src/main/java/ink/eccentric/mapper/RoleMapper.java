package ink.eccentric.mapper;

import ink.eccentric.domain.vo.role.RoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author eccentric
 */
@Mapper
public interface RoleMapper {

    /**
     * 获取角色列表(key)通过用户的id
     * @param userId
     * @return
     */
    public List<String> getRolesKeyByUserId(@Param("userId") Long userId);

    /**
     * 获取角色列表(id)通过用户id
     * @param userId
     * @return
     */
    public List<Integer> getRolesIdByUserId(@Param("userId") Long userId);

    /**
     * 获取所有角色列表
     * @return
     */
    public List<RoleVo> getRolesList(@Param("status") String status);
}
