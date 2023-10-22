package ink.eccentric.service.impl;

import ink.eccentric.constants.SystemConstants;
import ink.eccentric.domain.po.Role;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.mapper.RoleMapper;
import ink.eccentric.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author eccentric
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> getRolesKeyByUserId(Long id) {
        return roleMapper.getRolesKeyByUserId(id);
    }

    @Override
    public List<Integer> getRolesIdByUserId(Long id) {
        return roleMapper.getRolesIdByUserId(id);
    }

    @Override
    public Result getAllRoles() {
        return Result.ok(roleMapper.getRolesList(SystemConstants.NORMAL_STATUS));
    }
}
