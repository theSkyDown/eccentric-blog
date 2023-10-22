package ink.eccentric.service;

import ink.eccentric.domain.po.Role;
import ink.eccentric.domain.vo.common.Result;

import java.util.List;

/**
 * 角色服务
 * @author eccentric
 */
public interface RoleService {

    /**
     * 获取角色列表(key)通过用户的id
     * @param id
     * @return
     */
    public List<String> getRolesKeyByUserId(Long id);


    /**
     * 获取角色列表(id)通过用户的id
     * @param id
     * @return
     */
    public List<Integer> getRolesIdByUserId(Long id);

    /**
     * 获取所有角色列表
     * @return
     */
    public Result getAllRoles();

}
