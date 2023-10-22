package ink.eccentric.service;

import java.util.List;

/**
 * @author eccentric
 */
public interface UserRoleService {

    /**
     * 添加用户角色
     * @param id
     * @param roleIds
     * @return
     */
    public Integer addUserRole(Long id, List<Long> roleIds);

    /**
     * 获取用户的角色id列表
     * @param userId
     * @return
     */
    public List<Long> getRoleIdsByUserId(Long userId);

    /**
     * 删除用户的所有角色
     * @param userId
     * @return
     */
    public Integer deleteAllRolesByUserId(Long userId);
}
