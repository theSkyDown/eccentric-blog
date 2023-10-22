package ink.eccentric.service;

import ink.eccentric.domain.dto.category.CategoryDto;
import ink.eccentric.domain.vo.common.Result;

import java.util.List;

/**
 * @author eccentric
 */
public interface CategoryService{
    /**
     * 获取分类列表
     * @return
     */
    public Result getCategoryList();

    /**
     * 获取分类数量
     * @return
     */
    public Integer getCategoryCount();

    /**
     * 分页获取分类列表
     * @param pageNum
     * @param pageSize
     * @param name
     * @param status
     * @return
     */
    public Result getCategoryPagesList(Integer pageNum, Integer pageSize, String name, String status);

    /**
     * 添加分类
     * @param categoryDto
     * @return
     */
    public Result addCategory(CategoryDto categoryDto);

    /**
     * 获取分类信息通过id
     * @param id
     * @return
     */
    public Result getCategoryById(Long id);

    /**
     * 修改分类
     * @param categoryDto
     * @return
     */
    public Result updateCategory(CategoryDto categoryDto);

    /**
     * 修改分类状态
     * @param categoryDto
     * @return
     */
    public Result changeStatus(CategoryDto categoryDto);

    /**
     * 删除分类
     * @param ids
     * @return
     */
    public Result deleteCategory(List<Long> ids);
}
