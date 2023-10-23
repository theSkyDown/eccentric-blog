package ink.eccentric.service.impl;

import ink.eccentric.constants.SystemConstants;
import ink.eccentric.domain.dto.role.AddRoleDto;
import ink.eccentric.domain.dto.role.UpdateRoleDto;
import ink.eccentric.domain.dto.role.UpdateRoleStatusDto;
import ink.eccentric.domain.po.Role;
import ink.eccentric.domain.po.User;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.domain.vo.role.RoleVo;
import ink.eccentric.domain.vo.role.UpdateRoleVo;
import ink.eccentric.domain.vo.user.UpdateUserInfoVo;
import ink.eccentric.mapper.RoleMapper;
import ink.eccentric.service.MenuService;
import ink.eccentric.service.RoleMenuService;
import ink.eccentric.service.RoleService;
import ink.eccentric.utils.BeanCopyUtil;
import ink.eccentric.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * @author eccentric
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuService roleMenuService;



    @Override
    public List<String> getRolesKeyByUserId(Long id) {
        return roleMapper.getRolesKeyByUserId(id);
    }

    @Override
    public List<Long> getRolesIdByUserId(Long id) {
        return roleMapper.getRolesIdByUserId(id);
    }

    @Override
    public Result getAllRoles() {
        return Result.ok(roleMapper.getRolesList(null,null,null,SystemConstants.NORMAL_STATUS));
    }

    @Override
    public Result getRolesPageList(Integer pageNum, Integer pageSize, String roleName, String status) {
        Map<String,Object> res = new HashMap<>();
        List<RoleVo> rolesList = roleMapper.getRolesList(pageNum - 1, pageSize, roleName, status);
        res.put("rows",rolesList);
        Integer count = roleMapper.getRolesCount(roleName,status);
        res.put("total",count);
        return Result.ok(res);
    }

    @Override
    @Transactional
    public Result addRole(AddRoleDto addRoleDto) {
        Result result = checkExist(addRoleDto);
        if (Objects.nonNull(result)){
            return result;
        }
        Role role = BeanCopyUtil.copyBean(addRoleDto, Role.class);
        //设置一些其他信息，如创建时间、创建人、更新时间、更新人
        //创建人
        role.setCreateBy(SecurityUtil.getUser().getId());
        //更新人
        role.setUpdateBy(SecurityUtil.getUser().getId());
        //创建时间
        role.setCreateTime(new Date());
        //更新时间
        role.setUpdateTime(new Date());
        Integer addRoleCount = roleMapper.addRole(role);
        //如果没添加成功则回滚
        if (addRoleCount < 1){
            //执行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail();
        }
        if (addRoleDto.getMenuIds().size() > 0){
            roleMenuService.addRoleMenu(role.getId(),addRoleDto.getMenuIds());
        }
        return Result.ok();
    }

    @Override
    public Result deleteRole(List<Long> idList) {
        for (Long baseRoleId : SystemConstants.BASE_ROLE_ID_LIST) {
            if (idList.contains(baseRoleId)){
                return Result.fail().message("无法删除基础角色");
            }
        }
        return roleMapper.deleteRole(idList,SecurityUtil.getUser().getId(),new Date())>0?Result.ok():Result.fail();
    }

    @Override
    public Result getRoleDetail(Long id) {
        //获取角色的信息
        UpdateRoleVo role = roleMapper.getRoleDetailById(id);
        //获取该角色的菜单id列表
        List<Long> menuIds = roleMenuService.getMenuIdListByRoleId(id);
        role.setMenuIds(menuIds);
        return Result.ok(role);
    }

    @Override
    @Transactional
    public Result updateRole(UpdateRoleDto updateRoleDto) {
        Result result = checkExist(updateRoleDto);
        if (Objects.nonNull(result)){
            return result;
        }
        Role role = BeanCopyUtil.copyBean(updateRoleDto, Role.class);
        //更新人
        role.setUpdateBy(SecurityUtil.getUser().getId());
        //更新时间
        role.setUpdateTime(new Date());
        Integer updateRoleCount = roleMapper.updateRole(role);
        //如果没修改成功，则回滚
        if (updateRoleCount<1){
            //执行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail();
        }
        //删除该角色的所有菜单
        roleMenuService.deleteAllMenusByRoleId(role.getId());
        //重新添加对应的菜单
        roleMenuService.addRoleMenu(role.getId(),updateRoleDto.getMenuIds());
        return Result.ok();
    }

    @Override
    public Result changeStatus(UpdateRoleStatusDto updateRoleStatusDto) {
        for (Long baseRoleId : SystemConstants.BASE_ROLE_ID_LIST) {
            if (baseRoleId.equals(updateRoleStatusDto.getId())){
                return Result.fail().message("无法修改基础角色的状态");
            }
        }
        Role role = BeanCopyUtil.copyBean(updateRoleStatusDto,Role.class);
        role.setUpdateBy(SecurityUtil.getUser().getId());
        role.setUpdateTime(new Date());
        return roleMapper.changeStatus(role)==1?Result.ok():Result.fail();
    }

    /**
     * 检查添加的角色一些信息是否已经存在
     * @param addRoleDto
     * @return
     */
    private Result checkExist(AddRoleDto addRoleDto){
        Role role = roleMapper.getRoleByKey(addRoleDto.getRoleKey());
        if (Objects.nonNull(role)){
            return Result.fail().message("角色的权限字符已存在");
        }
        return null;
    }

    /**
     * 检查添加的角色一些信息是否已经存在
     * @param updateRoleDto
     * @return
     */
    private Result checkExist(UpdateRoleDto updateRoleDto){
        Role role = roleMapper.getRoleByKey(updateRoleDto.getRoleKey());
        if (Objects.nonNull(role) && !role.getId().equals(updateRoleDto.getId())){
            return Result.fail().message("角色的权限字符已存在");
        }
        return null;
    }

}
