package ink.eccentric.service.impl;

import ink.eccentric.domain.po.User;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.domain.vo.menu.RoutesVo;
import ink.eccentric.mapper.MenuMapper;
import ink.eccentric.service.MenuService;
import ink.eccentric.service.RoleService;
import ink.eccentric.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author eccentric
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleService roleService;

    @Override
    public Result getRoutes() {
        Map<String,Object> res = new HashMap<>();
        User user = SecurityUtil.getUser();
        if (Objects.isNull(user)){
            return Result.fail().message("用户信息异常");
        }
        //获取用户的角色
        List<Integer> roles = roleService.getRolesIdByUserId(user.getId());
        //获取 当前角色 的所有根菜单,并将其转换为Vo
        List<RoutesVo> routesVoList = menuMapper.getRootMenus(roles);
        //将其子节点与根节点关联起来
        for (RoutesVo routeVo : routesVoList){
            //根据评论的id查找，对应的子节点
            routeVo.setChildren(menuMapper.getChildren(routeVo.getId(),roles));
        }
        res.put("menus",routesVoList);
        //封装Result返回
        return Result.ok(res);
    }
}
