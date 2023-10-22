package ink.eccentric.controller;

import ink.eccentric.annotation.SystemLog;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 标签
 * @author eccentric
 */
@RestController
@RequestMapping("/tag")
@Api(tags = "标签",description = "标签相关接口")
public class TagController extends BaseController {

    @Autowired
    private TagService tagService;

    /**
     * 获取标签数量
     * @return
     */
    @GetMapping("/tagCount")
    @SystemLog(businessName = "获取标签数量")
    @ApiOperation(value = "获取标签数量",notes = "获取标签数量")
    public Result tagCount(){
        return Result.ok(tagService.getTagCount());
    }


    /**
     * 获取标签列表
     * @return
     */
    @GetMapping("/list")
    @SystemLog(businessName = "获取标签列表")
    @ApiOperation(value = "获取标签列表",notes = "获取标签列表")
    public Result list(){
        return tagService.getTagList();
    }



}
