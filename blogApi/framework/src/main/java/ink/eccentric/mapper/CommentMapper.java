package ink.eccentric.mapper;

import ink.eccentric.domain.po.Comment;
import ink.eccentric.domain.vo.comment.CommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author eccentric
 */
@Mapper
public interface CommentMapper {

    /**
     * 获取文章的评论列表
     * @param articleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<CommentVo> getCommentList(@Param("type") String type,@Param("articleId") Long articleId,@Param("pageNum") Integer pageNum,@Param("pageSize") Integer pageSize);

    /**
     * 添加评论
     * @param comment
     * @return
     */
    public Integer insertComment(Comment comment);

    /**
     * 查询父评论(root_id)为 id 的评论
     * @param rootId
     * @param articleId
     * @return
     */
    public List<CommentVo> getChildren(@Param("type") String type, @Param("rootId") Long rootId,@Param("articleId") Long articleId);

    /**
     * 获取所有根评论的数量
     * @param type
     * @param articleId
     * @return
     */
    public Integer getCommentCount(@Param("type") String type,@Param("articleId") Long articleId);
}
