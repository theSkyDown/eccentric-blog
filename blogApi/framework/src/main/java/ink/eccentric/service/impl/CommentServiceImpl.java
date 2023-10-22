package ink.eccentric.service.impl;

import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.domain.dto.comment.SendCommentDto;
import ink.eccentric.domain.po.Comment;
import ink.eccentric.domain.vo.comment.CommentVo;
import ink.eccentric.enums.AppHttpCodeEnum;
import ink.eccentric.exception.SystemException;
import ink.eccentric.mapper.CommentMapper;
import ink.eccentric.service.CommentService;
import ink.eccentric.utils.BeanCopyUtil;
import ink.eccentric.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author eccentric
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

//    废弃 —— 因为这种做法，会导致分页功能异常，例如 size=10 变成了：父评论+子评论=10
//           我们希望是 父评论=10，子评论可以限制也可以不限制

//    @Override
//    public Result getCommentList(Long articleId, Integer pageNum, Integer pageSize) {
//        //获取该文章的所有评论,并将其转换为Vo
//        List<CommentVo> commentList = commentMapper.getCommentList(articleId, pageNum - 1, pageSize);
//        //将其子评论与跟评论关联起来
//        List<CommentVo> resultList = getChildren(commentList, -1L);
//        //封装Result返回
//        return Result.ok(resultList);
//    }


    @Override
    public Integer getCommentCount(String type, Long articleId) {
        return commentMapper.getCommentCount(type,articleId);
    }

    @Override
    public Result getCommentList(String type,Long articleId, Integer pageNum, Integer pageSize) {
        //获取该文章(友联)的所有根评论,并将其转换为Vo
        List<CommentVo> commentList = commentMapper.getCommentList(type, articleId, pageNum - 1, pageSize);
        //将其子评论与跟评论关联起来
        for (CommentVo commentVo : commentList){
            //根据评论的id查找，对应的子评论
            commentVo.setChildren(commentMapper.getChildren(type,commentVo.getId(),articleId));
        }
        //封装Result返回
        return Result.ok(commentList);
    }


    @Override
    public Result sendComment(SendCommentDto commentDto) {
        //拦截评论内容为空的数据
        if (!StringUtils.hasText(commentDto.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NULL);
        }
        //将前端传来的数据进行Bean拷贝成Comment，用于后续的添加
        Comment comment = BeanCopyUtil.copyBean(commentDto,Comment.class);
        //设置一些其他信息，如创建时间、创建人、更新时间、更新人
        //创建人
        comment.setCreateBy(SecurityUtil.getUser().getId());
        //更新人
        comment.setUpdateBy(SecurityUtil.getUser().getId());
        //创建时间
        comment.setCreateTime(new Date());
        //更新时间
        comment.setUpdateTime(new Date());
        //封装成Result
        return commentMapper.insertComment(comment)==1?Result.ok():Result.fail();
    }


    /**
     * 将子评论与根评论关联 (暂时废弃)
     * @param list
     * @param start
     */
    private List<CommentVo> getChildren(List<CommentVo> list,Long start){
        List<CommentVo> result = new ArrayList<>();
        for (CommentVo commentVo : list){
            if (commentVo.getRootId().equals(start)){
                commentVo.setChildren(getChildren(list,commentVo.getId()));
                result.add(commentVo);
            }
        }
        return result;
    }
}
