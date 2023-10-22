package ink.eccentric.utils;

import ink.eccentric.domain.vo.common.UserDetailsImpl;
import ink.eccentric.domain.po.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * Security工具类——用户获取当前用户的信息
 * @author eccentric
 */
public class SecurityUtil {

    /**
     * 获取authentication
     * @return
     */
    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户信息(包括权限列表)
     * @return
     */
    public static UserDetailsImpl getUserDetail(){
        return (UserDetailsImpl) getAuthentication().getPrincipal();
    }

    /**
     * 获取用户信息
     * @return
     */
    public static User getUser(){
        return getUserDetail().getUser();
    }

    public static List<String> getPermissionList(){
        return getUserDetail().getPermissions();
    }

}
