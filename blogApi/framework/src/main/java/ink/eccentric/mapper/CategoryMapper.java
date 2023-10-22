package ink.eccentric.mapper;


import ink.eccentric.domain.po.Category;
import ink.eccentric.domain.vo.category.CategoryVo;
import ink.eccentric.domain.vo.common.Result;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author eccentric
 */
@Mapper
public interface CategoryMapper{

    /**
     * 获取分类列表
     * @return
     */
    public List<CategoryVo> getCategoryList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("name") String name, @Param("status") String status);

    /**
     * 获取分类数量
     * @return
     */
    public Integer getCategoryCount(@Param("name") String name, @Param("status") String status);

    /**
     * 添加分类
     * @param category
     * @return
     */
    public Integer insertCategory(Category category);

    /**
     * 获取分类信息通过id
     * @param id
     * @return
     */
    public CategoryVo getCategoryById(@Param("id") Long id);

    /**
     * 修改分类
     * @param category
     * @return
     */
    public Integer updateCategory(Category category);

    /**
     * 删除分类
     * @param ids
     * @param updateBy
     * @param updateTime
     * @return
     */
    public Integer deleteCategory(@Param("ids") List<Long> ids, @Param("updateBy") Long updateBy,@Param("updateTime") Date updateTime);
}
