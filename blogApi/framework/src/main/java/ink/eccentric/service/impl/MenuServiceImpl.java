package ink.eccentric.service.impl;

import ink.eccentric.domain.dto.menu.MenuDto;
import ink.eccentric.domain.po.Menu;
import ink.eccentric.domain.po.User;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.domain.vo.menu.MenuTreeSelectVo;
import ink.eccentric.domain.vo.menu.RoutesVo;
import ink.eccentric.mapper.MenuMapper;
import ink.eccentric.service.MenuService;
import ink.eccentric.service.RoleService;
import ink.eccentric.utils.BeanCopyUtil;
import ink.eccentric.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        List<Long> roles = roleService.getRolesIdByUserId(user.getId());
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


    @Override
    public Result getTreeSelect() {
        //获取所有根菜单，并将其转换为Vo
        List<MenuTreeSelectVo> menuTreeSelectList = menuMapper.getAllRootMenus();
        //将其子节点关联起来
        for (MenuTreeSelectVo menuTreeSelectVo : menuTreeSelectList){
            menuTreeSelectVo.setChildren(menuMapper.getTreeSelectListChildren(menuTreeSelectVo.getId()));
        }
        return Result.ok(menuTreeSelectList);
    }

    @Override
    public Result list(String status, String menuName) {
        return Result.ok(menuMapper.list(status,menuName));
    }

    @Override
    public Result addMenu(MenuDto menuDto) {
        Menu menu = BeanCopyUtil.copyBean(menuDto, Menu.class);
        menu.setCreateBy(SecurityUtil.getUser().getId());
        menu.setUpdateBy(SecurityUtil.getUser().getId());
        menu.setCreateTime(new Date());
        menu.setUpdateTime(new Date());
        return menuMapper.addMenu(menu)>0?Result.ok():Result.fail();
    }

    @Override
    public Result deleteMenu(Long id){
        if (menuMapper.getChildMenuCount(id)>0){
            return Result.fail().message("存在子菜单,不允许删除");
        }
        return menuMapper.deleteMenu(id,SecurityUtil.getUser().getId(),new Date())>0?Result.ok():Result.fail();
    }

    @Override
    public Result getMenuDetail(Long id) {
        return Result.ok(menuMapper.getMenuById(id));
    }

    @Override
    public Result updateMenu(MenuDto menuDto) {
        Menu menu = BeanCopyUtil.copyBean(menuDto, Menu.class);
        menu.setUpdateBy(SecurityUtil.getUser().getId());
        menu.setUpdateTime(new Date());
        return menuMapper.updateMenu(menu)>0?Result.ok():Result.fail();
    }
}
