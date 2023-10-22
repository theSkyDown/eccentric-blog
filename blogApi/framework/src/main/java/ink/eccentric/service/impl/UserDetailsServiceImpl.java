package ink.eccentric.service.impl;


import ink.eccentric.domain.vo.common.UserDetailsImpl;
import ink.eccentric.domain.po.User;
import ink.eccentric.enums.AppHttpCodeEnum;
import ink.eccentric.exception.SystemException;
import ink.eccentric.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author eccentric
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查找用户
        User user = userMapper.getUserByUsername(username);
        if (Objects.isNull(user)){
            throw new SystemException(AppHttpCodeEnum.USER_NOT_EXIST);
        }
        //查找用户所拥有的权限
        List<String> permissions = userMapper.getUserPermissions(user.getId());
        return new UserDetailsImpl(user,permissions);
    }

}
