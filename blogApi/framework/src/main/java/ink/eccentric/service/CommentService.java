package ink.eccentric.service;


import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.domain.dto.comment.SendCommentDto;

/**
 * @author eccentric
 */
public interface CommentService {

    /**
     * 获取文章的评论列表
     * @param articleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Result getCommentList(String commentType,Long articleId, Integer pageNum, Integer pageSize);

    /**
     * 给文章(友链)发送评论
     * @param commentDto
     * @return
     */
    public Result sendComment(SendCommentDto commentDto);

    /**
     * 获取所有根评论的数量
     * @param articleComment
     * @param articleId
     * @return
     */
    public Integer getCommentCount(String articleComment, Long articleId);
}
