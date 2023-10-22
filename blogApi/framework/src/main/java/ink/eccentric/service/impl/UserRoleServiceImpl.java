package ink.eccentric.service.impl;

import ink.eccentric.mapper.UserRoleMapper;
import ink.eccentric.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author eccentric
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public Integer addUserRole(Long id, List<Long> roleIds) {
        return userRoleMapper.addUserRole(id,roleIds);
    }

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        return userRoleMapper.getRoleIdsByUserId(userId);
    }

    @Override
    public Integer deleteAllRolesByUserId(Long userId) {
        return userRoleMapper.deleteAllRolesByUserId(userId);
    }
}
