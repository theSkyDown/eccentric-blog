package ink.eccentric.mapper;

import ink.eccentric.domain.dto.role.UpdateRoleStatusDto;
import ink.eccentric.domain.po.Role;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.domain.vo.role.RoleVo;
import ink.eccentric.domain.vo.role.UpdateRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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
    public List<Long> getRolesIdByUserId(@Param("userId") Long userId);

    /**
     * 获取所有角色列表
     * @return
     */
    public List<RoleVo> getRolesList(@Param("pageNum") Integer pageNum,@Param("pageSize") Integer pageSize,@Param("roleName") String roleName,@Param("status") String status);

    /**
     * 获取角色数量
     * @param roleName
     * @param status
     * @return
     */
    public Integer getRolesCount(@Param("roleName") String roleName,@Param("status") String status);

    /**
     * 通过权限字符获取角色
     * @param roleKey
     * @return
     */
    public Role getRoleByKey(@Param("roleKey") String roleKey);

    /**
     * 添加角色
     * @param role
     * @return
     */
    public Integer addRole(Role role);

    /**
     * 删除角色
     * @return
     */
    public Integer deleteRole(@Param("ids") List<Long> ids, @Param("updateBy") Long updateBy,@Param("updateTime") Date updateTime);

    /**
     * 获取角色详细信息
     * @param id
     * @return
     */
    public UpdateRoleVo getRoleDetailById(@Param("id") Long id);

    /**
     * 更新角色
     * @param role
     * @return
     */
    public Integer updateRole(Role role);

    /**
     *  修改角色的状态
     * @param role
     * @return
     */
    public Integer changeStatus(Role role);
}
