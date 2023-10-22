package ink.eccentric.controller;

import ink.eccentric.annotation.SystemLog;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.enums.AppHttpCodeEnum;
import ink.eccentric.exception.SystemException;
import ink.eccentric.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author eccentric
 */
@RestController
@RequestMapping("/article")
@Api(tags = "文章",description = "文章相关接口")
public class ArticleController extends BaseController{

    @Autowired
    private ArticleService articleService;


//    @GetMapping("/list")
//    public List<Article> list(){
//        return articleService.list();
//    }

    /**
     * 获取热门文章
     * @return
     */
    @GetMapping("/hotArticleList")
    @SystemLog(businessName = "获取热门文章")
    @ApiOperation(value = "获取热门文章列表",notes = "获取热门文章列表")
    public Result hotArticleList(){
        return articleService.hotArticleList();
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
            @ApiImplicitParam(name = "categoryId",value = "文章id"),
            @ApiImplicitParam(name = "tagId",value = "标签id")
    })
    public Result list(@RequestParam Integer pageNum,@RequestParam Integer pageSize,Long categoryId,Long tagId){
        //拦截不正常参数
        if (notOk(pageNum) || notOk(pageNum)){
            return Result.fail(AppHttpCodeEnum.PARAMS_FAIL.getCode(),AppHttpCodeEnum.PARAMS_FAIL.getMsg());
        }
        Integer count = articleService.articleListCount(categoryId,tagId,null,null);
        return articleService.articleList(pageNum,pageSize,categoryId,tagId,null,null).count(count);
    }


    /**
     * 获取文章详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @SystemLog(businessName = "获取文章详情")
    @ApiOperation(value = "获取文章详情",notes = "获取文章详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "文章id")
    })
    public Result articleDetail(@PathVariable Long id){
        if (notOk(id)){
            throw new SystemException(AppHttpCodeEnum.PARAMS_FAIL);
        }
        return articleService.articleDetail(id);
    }

    /**
     * 更新浏览量
     * @param id
     * @return
     */
    @PutMapping("/updateViewCount/{id}")
    @SystemLog(businessName = "更新浏览量")
    @ApiOperation(value = "更新浏览量",notes = "更新浏览量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "文章id")
    })
    public Result updateViewCount(@PathVariable Long id){
        if (notOk(id)){
            throw new SystemException(AppHttpCodeEnum.PARAMS_FAIL);
        }
        return articleService.updateViewCount(id);
    }

    /**
     * 获取文章的数量
     * @return
     */
    @GetMapping("/articleCount")
    @SystemLog(businessName = "获取文章的数量")
    @ApiOperation(value = "获取文章的数量",notes = "获取文章的数量")
    public Result articleCount(){
        return Result.ok(articleService.articleListCount(null,null,null,null));
    }


    /**
     * （归档）分页查询博文列表，并且进行时间排序，最新的在前
     * @return
     */
    @GetMapping("/pigeonholeList")
    @SystemLog(businessName = "(归档)分页查询博文列表")
    @ApiOperation(value = "(归档)分页查询博文列表",notes = "(归档)分页查询博文列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "分页参数"),
            @ApiImplicitParam(name = "pageSize",value = "分页参数"),
    })
    public Result pigeonholeList(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        //拦截不正常参数
        if (notOk(pageNum) || notOk(pageNum)){
            throw new SystemException(AppHttpCodeEnum.PARAMS_FAIL);
        }
        Integer count = articleService.pigeonholeListCount();
        return articleService.pigeonholeList(pageNum,pageSize).count(count);
    }


    /**
     * 获取该文章的前一篇文章与后一篇文章
     * @param id
     * @return
     */
    @GetMapping("/aroundArticle/{id}")
    @SystemLog(businessName = "获取该文章的前一篇文章与后一篇文章")
    @ApiOperation(value = "获取该文章的前一篇文章与后一篇文章",notes = "获取该文章的前一篇文章与后一篇文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "文章id")
    })
    public Result aroundArticle(@PathVariable Long id){
        if (notOk(id)){
            return Result.fail(400,"参数异常");
        }
        return articleService.aroundArticle(id);
    }
}
