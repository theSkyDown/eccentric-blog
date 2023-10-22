package ink.eccentric.controller;

import ink.eccentric.annotation.SystemLog;
import ink.eccentric.constants.SystemConstants;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.domain.dto.comment.SendCommentDto;
import ink.eccentric.enums.AppHttpCodeEnum;
import ink.eccentric.exception.SystemException;
import ink.eccentric.service.CommentService;
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
@RequestMapping("/comment")
@Api(tags = "评论",description = "评论相关接口")
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取文章的评论列表
     * @return
     */
    @GetMapping("/list")
    @SystemLog(businessName = "获取文章的评论列表")
    @ApiOperation(value = "获取文章的评论列表",notes = "获取文章的评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId",value = "文章id"),
            @ApiImplicitParam(name = "pageNum",value = "分页参数"),
            @ApiImplicitParam(name = "pageSize",value = "分页参数")
    })
    public Result list(@RequestParam Long articleId,@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        if (notOk(articleId) || notOk(pageNum) || notOk(pageSize)){
            throw new SystemException(AppHttpCodeEnum.PARAMS_FAIL);
        }
        Integer count = commentService.getCommentCount(SystemConstants.ARTICLE_COMMENT,articleId);
        return commentService.getCommentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize).count(count);
    }


    /**
     * 给文章(友链)发送评论
     * @param commentDto
     * @return
     */
    @PostMapping("/sendComment")
    @SystemLog(businessName = "给文章(友链)发送评论")
    @ApiOperation(value = "给文章(友链)发送评论",notes = "给文章(友链)发送评论")
    public Result sendComment(@RequestBody SendCommentDto commentDto){
        if (notOk(commentDto)||
                notOk(commentDto.getContent())||
                notOk(commentDto.getArticleId())
        ){
            throw new SystemException(AppHttpCodeEnum.PARAMS_FAIL);
        }
        return commentService.sendComment(commentDto);
    }


    /**
     * 获取友链的评论列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/linkList")
    @SystemLog(businessName = "获取友链的评论列表")
    @ApiOperation(value = "获取友链的评论列表",notes = "获取友链的评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "分页参数"),
            @ApiImplicitParam(name = "pageSize",value = "分页参数"),
    })
    public Result linkList(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        if (notOk(pageNum) || notOk(pageSize)){
            throw new SystemException(AppHttpCodeEnum.PARAMS_FAIL);
        }
        return commentService.getCommentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }

}
