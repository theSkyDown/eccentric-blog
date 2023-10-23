package ink.eccentric.service;

import ink.eccentric.domain.dto.role.AddRoleDto;
import ink.eccentric.domain.dto.role.UpdateRoleDto;
import ink.eccentric.domain.dto.role.UpdateRoleStatusDto;
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
    public List<Long> getRolesIdByUserId(Long id);

    /**
     * 获取所有角色列表
     * @return
     */
    public Result getAllRoles();

    /**
     * 分页获取角色列表
     * @param pageNum
     * @param pageSize
     * @param roleName
     * @param status
     * @return
     */
    public Result getRolesPageList(Integer pageNum, Integer pageSize, String roleName, String status);

    /**
     * 添加角色
     * @param addRoleDto
     * @return
     */
    public Result addRole(AddRoleDto addRoleDto);

    /**
     * 删除角色
     * @param idList
     * @return
     */
    public Result deleteRole(List<Long> idList);

    /**
     * 获取角色的详细信息
     * @param id
     * @return
     */
    public Result getRoleDetail(Long id);

    /**
     * 修改角色
     * @param updateRoleDto
     * @return
     */
    public Result updateRole(UpdateRoleDto updateRoleDto);

    public Result changeStatus(UpdateRoleStatusDto updateRoleStatusDto);
}
