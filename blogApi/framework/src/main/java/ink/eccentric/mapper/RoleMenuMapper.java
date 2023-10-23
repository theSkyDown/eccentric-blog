package ink.eccentric.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author eccentric
 */
@Mapper
public interface RoleMenuMapper {


    /**
     * 添加角色菜单权限
     * @param roleId
     * @param menuIds
     * @return
     */
    public Integer addRoleMenu(@Param("roleId") Long roleId,@Param("menuIds") List<Long> menuIds);

    /**
     * 获取角色的菜单列表
     * @param roleId
     * @return
     */
    public List<Long> getMenuIdListByRoleId(@Param("roleId") Long roleId);

    /**
     * 删除角色的所有菜单
     * @param roleId
     * @return
     */
    public Integer deleteAllMenusByRoleId(@Param("roleId") Long roleId);
}
