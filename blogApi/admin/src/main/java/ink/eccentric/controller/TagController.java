package ink.eccentric.controller;

import ink.eccentric.annotation.SystemLog;
import ink.eccentric.domain.dto.tag.AddTagDto;
import ink.eccentric.domain.dto.tag.UpdateTagDto;
import ink.eccentric.domain.po.Tag;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.domain.vo.tag.TagVo;
import ink.eccentric.enums.AppHttpCodeEnum;
import ink.eccentric.exception.SystemException;
import ink.eccentric.service.TagService;
import ink.eccentric.utils.ParamsTransformationUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eccentric
 */
@RestController
@RequestMapping("/tag")
@Api(tags = "标签",description = "标签相关接口")
public class TagController extends BaseController{

    @Autowired
    private TagService tagService;

    /**
     * 获取所有标签(分页)
     * @return
     */
    @GetMapping("/list")
    @SystemLog(businessName = "分页获取标签")
    @ApiOperation(value = "分页获取标签",notes = "分页获取标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "分页参数"),
            @ApiImplicitParam(name = "pageSize",value = "分页参数"),
            @ApiImplicitParam(name = "name",value = "标签名"),
            @ApiImplicitParam(name = "remark",value = "备注")
    })
    public Result list(@RequestParam Integer pageNum,@RequestParam Integer pageSize,String name,String remark){
        //拦截不正常参数
        if (notOk(pageNum) || notOk(pageNum)){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        return tagService.getTagPagesList(pageNum,pageSize,name,remark);
    }

    /**
     * 添加标签
     * @param addTagDto
     * @return
     */
    @PostMapping("/add")
    @SystemLog(businessName = "添加标签")
    @ApiOperation(value = "添加标签",notes = "添加标签")
    public Result addTag(@RequestBody AddTagDto addTagDto){
        if (notOk(addTagDto)|| notOk(addTagDto.getName())){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        return tagService.addTag(addTagDto);
    }


    /**
     * 删除标签
     * @param ids
     * @return
     */
    @DeleteMapping("/delete/{ids}")
    @SystemLog(businessName = "删除标签(支持批量)")
    @ApiOperation(value = "删除标签(支持批量)",notes = "删除标签(支持批量)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "批量删除的id"),
    })
    public Result deleteTag(@PathVariable String ids) {
        List<Long> idList = null;
        try {
            idList = ParamsTransformationUtil.idsToList(ids);
        } catch (SystemException e) {
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(), AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        if (idList.size() < 1) {
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(), AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        return tagService.deleteTag(idList);
    }


    /**
     * 获取指定标签信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @SystemLog(businessName = "获取指定标签信息")
    @ApiOperation(value = "获取指定标签信息",notes = "获取指定标签信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id"),
    })
    public Result getTagById(@PathVariable Long id){
        if (notOk(id)){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        return tagService.getTagById(id);
    }

    /**
     * 修改标签信息
     * @param updateTagDto
     * @return
     */
    @PutMapping("/update")
    @SystemLog(businessName = "修改标签信息")
    @ApiOperation(value = "修改标签信息",notes = "修改标签信息")
    public Result updateTag(@RequestBody UpdateTagDto updateTagDto){
        if (notOk(updateTagDto.getId())){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        if (notOk(updateTagDto.getName())){
            return Result.fail().message("请填写内容");
        }
        return tagService.updateTag(updateTagDto);
    }

    /**
     * 获取标签列表
     * @return
     */
    @GetMapping("/listAllTag")
    @SystemLog(businessName = "获取所有标签列表")
    @ApiOperation(value = "获取所有标签列表",notes = "获取所有标签列表")
    public Result list(){
        return tagService.getTagList();
    }

}

