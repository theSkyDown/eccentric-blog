package ink.eccentric.mapper;

import ink.eccentric.domain.po.Menu;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.domain.vo.menu.MenuTreeSelectVo;
import ink.eccentric.domain.vo.menu.MenuVo;
import ink.eccentric.domain.vo.menu.RoutesVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author eccentric
 */
@Mapper
public interface MenuMapper {

    /**
     * 获取指定用户的根菜单
     * @return
     */
    public List<RoutesVo> getRootMenus(@Param("rolesId") List<Long> rolesId);


    /**
     * 获取指定根节点和指定用户的所有子菜单
     * @param id
     * @param rolesId
     * @return
     */
    public List<RoutesVo> getChildren(@Param("parentId") Long parentId,@Param("rolesId") List<Long> rolesId);

    /**
     * 获取所有根节点
     * @return
     */
    public List<MenuTreeSelectVo> getAllRootMenus();

    /**
     * 获取指定根节点的所有子菜单
     * @param parentId
     * @return
     */
    public List<MenuTreeSelectVo> getTreeSelectListChildren(@Param("parentId") Long parentId);

    /**
     * 获取菜单列表
     * @param status
     * @param menuName
     * @return
     */
    public List<MenuVo> list(@Param("status") String status,@Param("menuName") String menuName);

    /**
     * 添加菜单
     * @param menu
     * @return
     */
    public Integer addMenu(Menu menu);

    /**
     * 获取子菜单的数量
     * @param parentId
     * @return
     */
    public Integer getChildMenuCount(@Param("parentId") Long parentId);

    /**
     * 删除菜单
     * @param id
     * @return
     */
    public Integer deleteMenu(@Param("id") Long id, @Param("updateBy") Long updateBy, @Param("updateTime") Date updateTime);

    /**
     * 获取菜单详细信息
     * @param id
     * @return
     */
    public MenuVo getMenuById(Long id);


    /**
     * 修改菜单
     * @param menu
     * @return
     */
    public Integer updateMenu(Menu menu);
}
