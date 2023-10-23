package ink.eccentric.service.impl;

import ink.eccentric.mapper.RoleMenuMapper;
import ink.eccentric.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author eccentric
 */
@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public Integer addRoleMenu(Long roleId, List<Long> menuIds) {
        if (menuIds.size()==0){
            return 0;
        }
        return roleMenuMapper.addRoleMenu(roleId,menuIds);
    }


    @Override
    public List<Long> getMenuIdListByRoleId(Long roleId) {
        return roleMenuMapper.getMenuIdListByRoleId(roleId);
    }

    @Override
    public Integer deleteAllMenusByRoleId(Long roleId) {
        return roleMenuMapper.deleteAllMenusByRoleId(roleId);
    }
}
