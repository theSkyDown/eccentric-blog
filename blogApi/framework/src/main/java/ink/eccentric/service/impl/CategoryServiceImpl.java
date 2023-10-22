package ink.eccentric.service.impl;

import ink.eccentric.constants.SystemConstants;
import ink.eccentric.domain.dto.category.CategoryDto;
import ink.eccentric.domain.po.Category;
import ink.eccentric.domain.po.Tag;
import ink.eccentric.domain.vo.category.CategoryVo;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.domain.vo.tag.TagVo;
import ink.eccentric.mapper.CategoryMapper;
import ink.eccentric.service.CategoryService;
import ink.eccentric.utils.BeanCopyUtil;
import ink.eccentric.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author eccentric
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Result getCategoryList() {
        return Result.ok(categoryMapper.getCategoryList(null,null,null,SystemConstants.NORMAL_STATUS));
    }

    @Override
    public Integer getCategoryCount() {
        return categoryMapper.getCategoryCount(null, SystemConstants.NORMAL_STATUS);
    }

    @Override
    public Result getCategoryPagesList(Integer pageNum, Integer pageSize, String name, String status) {
        Map<String,Object> res = new HashMap<>();
        List<CategoryVo> categories = categoryMapper.getCategoryList(pageNum-1,pageSize,name,status);
        res.put("rows",categories);
        Integer count = categoryMapper.getCategoryCount(name,status);
        res.put("total",count);
        return Result.ok(res);
    }

    @Override
    public Result addCategory(CategoryDto categoryDto) {
        Category category = BeanCopyUtil.copyBean(categoryDto, Category.class);
        //创建人
        category.setCreateBy(SecurityUtil.getUser().getId());
        //更新人
        category.setUpdateBy(SecurityUtil.getUser().getId());
        //创建时间
        category.setCreateTime(new Date());
        //更新时间
        category.setUpdateTime(new Date());
        //封装
        return categoryMapper.insertCategory(category)==1?Result.ok():Result.fail();
    }

    @Override
    public Result getCategoryById(Long id) {
        return Result.ok(categoryMapper.getCategoryById(id));
    }

    @Override
    public Result updateCategory(CategoryDto categoryDto) {
        Category category = BeanCopyUtil.copyBean(categoryDto, Category.class);
        //更新人
        category.setUpdateBy(SecurityUtil.getUser().getId());
        //更新时间
        category.setUpdateTime(new Date());
        return categoryMapper.updateCategory(category)==1?Result.ok():Result.fail();
    }

    @Override
    public Result changeStatus(CategoryDto categoryDto) {
        Category category = BeanCopyUtil.copyBean(categoryDto, Category.class);
        //更新人
        category.setUpdateBy(SecurityUtil.getUser().getId());
        //更新时间
        category.setUpdateTime(new Date());
        return categoryMapper.updateCategory(category)==1?Result.ok():Result.fail();
    }

    @Override
    public Result deleteCategory(List<Long> ids) {
        return categoryMapper.deleteCategory(ids,SecurityUtil.getUser().getId(),new Date())>0?Result.ok():Result.fail();
    }
}
