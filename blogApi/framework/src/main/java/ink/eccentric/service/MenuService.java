package ink.eccentric.service;

import ink.eccentric.domain.dto.menu.MenuDto;
import ink.eccentric.domain.vo.common.Result;

/**
 * @author eccentric
 */
public interface MenuService {

    /**
     * 获取菜单列表
     * @return
     */
    public Result getRoutes();

    /**
     * 获取树形菜选项列表
     * @return
     */
    public Result getTreeSelect();

    /**
     * 获取菜单列表
     * @param status
     * @param menuName
     * @return
     */
    public Result list(String status, String menuName);

    /**
     * 添加菜单
     * @param menuDto
     * @return
     */
    public Result addMenu(MenuDto menuDto);

    /**
     * 删除菜单
     * @param id
     * @return
     */
    public Result deleteMenu(Long id);

    /**
     * 获取菜单详细信息
     * @param id
     * @return
     */
    public Result getMenuDetail(Long id);

    /**
     * 修改菜单
     * @param menuDto
     * @return
     */
    public Result updateMenu(MenuDto menuDto);
}
