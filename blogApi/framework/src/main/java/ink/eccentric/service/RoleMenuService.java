package ink.eccentric.service;

import java.util.List;

/**
 * @author eccentric
 */
public interface RoleMenuService {


    /**
     * 添加角色菜单权限
     * @param roleId
     * @param menuIds
     * @return
     */
    public Integer addRoleMenu(Long roleId, List<Long> menuIds);

    /**
     * 获取角色的菜单列表
     * @param roleId
     * @return
     */
    public List<Long> getMenuIdListByRoleId(Long roleId);

    /**
     * 删除所有菜单权限
     * @param roleId
     * @return
     */
    public Integer deleteAllMenusByRoleId(Long roleId);
}
