package ink.eccentric.mapper;

import ink.eccentric.domain.vo.menu.RoutesVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    public List<RoutesVo> getRootMenus(@Param("rolesId") List<Integer> rolesId);


    /**
     * 获取指定根节点和指定用户的所有子菜单
     * @param id
     * @param rolesId
     * @return
     */
    public List<RoutesVo> getChildren(@Param("parentId") Long parentId,@Param("rolesId") List<Integer> rolesId);
}
