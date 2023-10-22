package ink.eccentric.controller;

import ink.eccentric.annotation.SystemLog;
import ink.eccentric.domain.dto.category.CategoryDto;
import ink.eccentric.domain.vo.category.CategoryVo;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.enums.AppHttpCodeEnum;
import ink.eccentric.exception.SystemException;
import ink.eccentric.service.CategoryService;
import ink.eccentric.utils.ParamsTransformationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author eccentric
 */
@RestController
@RequestMapping("/category")
@Api(tags = "分类",description = "分类相关接口")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取分类列表
     * @return
     */
    @GetMapping("/listAllCategory")
    @SystemLog(businessName = "获取所有分类")
    @ApiOperation(value = "获取所有分类",notes = "获取所有分类")
    public Result listAllCategory(){
        return categoryService.getCategoryList();
    }


    /**
     * 获取分类列表
     * @return
     */
    @GetMapping("/list")
    @SystemLog(businessName = "分页获取分类列表")
    @ApiOperation(value = "分页获取分类列表",notes = "分页获取分类列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum",value = "分页参数"),
        @ApiImplicitParam(name = "pageNum",value = "分页参数"),
        @ApiImplicitParam(name = "name",value = "分类名"),
        @ApiImplicitParam(name = "status",value = "状态")
    })
    public Result list(@RequestParam Integer pageNum,@RequestParam Integer pageSize,String name,String status){
        //拦截不正常参数
        if (notOk(pageNum) || notOk(pageNum)){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        return categoryService.getCategoryPagesList(pageNum,pageSize,name,status);
    }

    /**
     * 添加分类
     * @param categoryDto
     * @return
     */
    @PostMapping("/add")
    @SystemLog(businessName = "添加分类")
    @ApiOperation(value = "添加分类",notes = "添加分类")
    public Result addCategory(@RequestBody CategoryDto categoryDto){
        if (notOk(categoryDto)|| notOk(categoryDto.getName())){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        return categoryService.addCategory(categoryDto);
    }

    /**
     * 获取指定标签信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @SystemLog(businessName = "获取指定分类信息")
    @ApiOperation(value = "获取指定分类信息",notes = "获取指定分类信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id"),
    })
    public Result getCategoryById(@PathVariable Long id){
        if (notOk(id)){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        return categoryService.getCategoryById(id);
    }

    /**
     * 修改分类
     * @param categoryDto
     * @return
     */
    @PutMapping("/update")
    @SystemLog(businessName = "修改分类")
    @ApiOperation(value = "修改分类",notes = "修改分类")
    public Result updateCategory(@RequestBody CategoryDto categoryDto){
        if (notOk(categoryDto.getId())){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        if (notOk(categoryDto.getName())){
            return Result.fail().message("请填写内容");
        }
        if (notOk(categoryDto.getStatus())){
            return Result.fail().message("请选择状态");
        }
        return categoryService.updateCategory(categoryDto);
    }

    /**
     * 修改分类状态
     * @param categoryDto
     * @return
     */
    @PutMapping("/changeStatus")
    @SystemLog(businessName = "修改分类状态")
    @ApiOperation(value = "修改分类状态",notes = "修改分类状态")
    public Result changeStatus(@RequestBody CategoryDto categoryDto){
        if (notOk(categoryDto.getId())){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        if (notOk(categoryDto.getStatus())){
            return Result.fail().message("请选择状态");
        }
        return categoryService.changeStatus(categoryDto);
    }

    /**
     * 删除分类
     * @param ids
     * @return
     */
    @DeleteMapping("/delete/{ids}")
    @SystemLog(businessName = "删除分类(支持批量)")
    @ApiOperation(value = "删除分类(支持批量)",notes = "删除分类(支持批量)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "批量删除的id"),
    })
    public Result deleteCategory(@PathVariable String ids) {
        List<Long> idList = null;
        try {
            idList = ParamsTransformationUtil.idsToList(ids);
        } catch (SystemException e) {
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(), AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        if (idList.size() < 1) {
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(), AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        return categoryService.deleteCategory(idList);
    }
}
