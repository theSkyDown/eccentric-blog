package ink.eccentric.controller;

import ink.eccentric.annotation.SystemLog;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分类
 * @author eccentric
 */
@RestController
@RequestMapping("/category")
@Api(tags = "分类",description = "分类相关接口")
public class CategoryController extends BaseController{

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取分类列表
     * @return
     */
    @GetMapping("/list")
    @SystemLog(businessName = "获取分类列表")
    @ApiOperation(value = "获取分类列表",notes = "获取分类列表")
    public Result list(){
        return categoryService.getCategoryList();
    }

    /**
     * 获取分类数量
     * @return
     */
    @GetMapping("/categoryCount")
    @SystemLog(businessName = "获取分类数量")
    @ApiOperation(value = "获取分类数量",notes = "获取分类数量")
    public Result categoryCount(){
        return Result.ok(categoryService.getCategoryCount());
    }
}
