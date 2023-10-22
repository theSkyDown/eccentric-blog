package ink.eccentric.service;

import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.domain.vo.menu.RoutesVo;

import java.util.List;

/**
 * @author eccentric
 */
public interface MenuService {

    /**
     * 获取菜单列表
     * @return
     */
    public Result getRoutes();
}
