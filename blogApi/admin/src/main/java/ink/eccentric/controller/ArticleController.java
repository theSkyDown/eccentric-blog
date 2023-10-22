package ink.eccentric.controller;

import ink.eccentric.annotation.SystemLog;
import ink.eccentric.domain.dto.article.ArticleDto;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.enums.AppHttpCodeEnum;
import ink.eccentric.exception.SystemException;
import ink.eccentric.service.ArticleService;
import ink.eccentric.utils.ParamsTransformationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author eccentric
 */
@RestController
@RequestMapping("/article")
@Api(tags = "文章",description = "文章相关接口")
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    /**
     * 添加文章
     * @return
     */
    @PostMapping("/add")
    @SystemLog(businessName = "添加文章")
    @ApiOperation(value = "添加文章",notes = "添加文章")
    public Result addArticle(@RequestBody ArticleDto articleDto){
        Result result = checkParams(articleDto,false);
        if (Objects.nonNull(result)){
            return result;
        }
        return articleService.addArticle(articleDto);
    }

    /**
     * 分页查询博文列表
     * @return
     */
    @GetMapping("/list")
    @SystemLog(businessName = "分页查询博文列表")
    @ApiOperation(value = "分页查询博文列表",notes = "分页查询博文列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "分页参数"),
            @ApiImplicitParam(name = "pageSize",value = "分页参数"),
            @ApiImplicitParam(name = "title",value = "文章标题"),
            @ApiImplicitParam(name = "summary",value = "文章摘要"),
    })
    public Result list(@RequestParam Integer pageNum,@RequestParam Integer pageSize,String title,String summary){
        Map<String,Object> map = new HashMap<>();
        //拦截不正常参数
        if (notOk(pageNum) || notOk(pageNum)){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        Integer count = articleService.articleListCount(null,null,title,summary);
        Result listResult = articleService.articleList(pageNum, pageSize, null, null, title, summary);
        map.put("total",count);
        map.put("rows",listResult.getData());
        return Result.ok(map);
    }


    /**
     * 查询文章详细
     * @return
     */
    @GetMapping("/{id}")
    @SystemLog(businessName = "查询文章详细")
    @ApiOperation(value = "查询文章详细",notes = "查询文章详细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "文章id"),
    })
    public Result getArticleById(@PathVariable Long id){
        return articleService.articleDetail(id);
    }

    /**
     * 修改文章
     * @return
     */
    @PutMapping("/update")
    @SystemLog(businessName = "修改文章")
    @ApiOperation(value = "修改文章",notes = "修改文章")
    public Result updateArticle(@RequestBody ArticleDto articleDto){
        Result result = checkParams(articleDto,false);
        if (Objects.nonNull(result)){
            return result;
        }
        return articleService.updateArticle(articleDto);
    }

    /**
     * 删除文章
     * @param ids
     * @return
     */
    @DeleteMapping("/delete/{ids}")
    @SystemLog(businessName = "删除文章(支持批量)")
    @ApiOperation(value = "删除文章(支持批量)",notes = "删除文章(支持批量)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "批量删除的id"),
    })
    public Result deleteArticle(@PathVariable String ids){
        List<Long> idList = null;
        try {
            idList = ParamsTransformationUtil.idsToList(ids);
        } catch (SystemException e) {
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(), AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        if (idList.size() < 1) {
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(), AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        return articleService.deleteArticle(idList);
    }


    /**
     * 用于检测必填参数
     * @param articleDto
     * @param idCheck 是否检测id
     * @return
     */
    private Result checkParams(ArticleDto articleDto,boolean idCheck){
        if (idCheck && notOk(articleDto.getId())){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        if (notOk(articleDto)){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        //文章标题
        if (notOk(articleDto.getTitle())){
            return Result.fail().message("文章标题不能为空");
        }
        //文章摘要
        if (notOk(articleDto.getSummary())){
            return Result.fail().message("文章摘要不能为空");
        }
        //文章内容
        if (notOk(articleDto.getContent())){
            return Result.fail().message("文章内容不能为空");
        }
        //文章缩略图
        if (notOk(articleDto.getThumbnail())){
            return Result.fail().message("请上传缩略图");
        }
        //分类
        if (notOk(articleDto.getCategoryId())){
            return Result.fail().message("请选择文章分类");
        }
        //标签
        if (notOk(articleDto.getTags())){
            return Result.fail().message("请选择文章标签");
        }
        return null;
    }
}
