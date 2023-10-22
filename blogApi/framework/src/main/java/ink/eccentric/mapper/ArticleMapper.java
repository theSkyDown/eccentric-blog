package ink.eccentric.mapper;

import ink.eccentric.domain.po.Article;
import ink.eccentric.domain.vo.article.*;
import ink.eccentric.domain.vo.common.Result;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author eccentric
 */
@Mapper
public interface ArticleMapper {


    /**
     * 获取热门文章
     * @return
     */
    public List<HotArticleVo> hotArticleList();

    /**
     * 获取文章列表(分页)
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    public List<ArticleListVo> articleList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("categoryId") Long categoryId, @Param("tagId") Long tagId,@Param("title") String title,@Param("summary") String summary);

    /**
     * 获取文章列表(分页)的数量
     * @param categoryId
     * @return
     */
    public Integer articleListCount(@Param("categoryId") Long categoryId,@Param("tagId") Long tagId,@Param("title") String title,@Param("summary") String summary);

    /**
     * 获取文章详细信息
     * @param id
     * @return
     */
    public ArticleDetailVo articleDetail(@Param("id") Long id);

    /**
     * 获取文章的浏览量
     * @return
     */
    public List<Article> getViewCounts();

    /**
     * 同步所有文章的浏览量
     * @param viewCounts
     * @return
     */
    public Integer updateViewCount(@Param("viewCounts") Map<Long, Long> viewCounts);


    /**
     * (归档)分页查询博文列表的数量
     * @return
     */
    public Integer pigeonholeListCount();

    /**
     * (归档)分页查询博文列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<PigeonholeArticleVo> pigeonholeList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    /**
     * 获取指定文章的上一篇文章
     * @param id
     * @return
     */
    public ArticleRoughVo getBeforeArticle(@Param("id") Long id);

    /**
     * 获取指定文章的下一篇文章
     * @param id
     * @return
     */
    public ArticleRoughVo getAfterArticle(@Param("id") Long id);

    /**
     * 添加文章
     * @param article
     * @return
     */
    public Integer addArticle(Article article);

    /**
     * 修改文章
     * @param article
     * @return
     */
    public Integer updateArticle(Article article);

    /**
     * 删除文章
     * @param ids
     * @param updateBy
     * @param updateTime
     * @return
     */
    public Integer deleteArticle(@Param("ids") List<Long> ids, @Param("updateBy") Long updateBy,@Param("updateTime") Date updateTime);
}
