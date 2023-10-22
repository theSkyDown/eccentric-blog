package ink.eccentric.service;

import ink.eccentric.domain.vo.common.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author eccentric
 */
public interface ArticleTagService {

    /**
     * 为文章添加标签
     * @param id
     * @param tags
     * @return
     */
    public Integer addArticleTag(Long id,List<Long> tags);

    /**
     * 获取标签列表通过文章id
     * @param id
     * @return
     */
    public List<Long> getTagsByArticleId(Long id);

    /**
     * 删除该文章的所有标签
     * @param id
     * @return
     */
    public Integer deleteAllArticleTagByArticleId(Long id);
}
