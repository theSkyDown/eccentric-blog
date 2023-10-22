package ink.eccentric.mapper;

import ink.eccentric.domain.vo.common.Result;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author eccentric
 */
@Mapper
public interface ArticleTagMapper {


    /**
     * 为文章添加标签
     * @param id
     * @param tags
     * @return
     */
    public Integer addArticleTag(@Param("id") Long id,@Param("tags") List<Long> tags);

    /**
     * 获取标签列表通过文章id
     * @param articleId
     * @return
     */
    public List<Long> getTagsByArticleId(@Param("articleId") Long articleId);


    /**
     * 删除该文章的所有标签
     * @param id
     * @return
     */
    public Integer deleteAllArticleTagByArticleId(@Param("articleId") Long articleId);
}
