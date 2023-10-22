package ink.eccentric.service;

import ink.eccentric.domain.dto.article.ArticleDto;
import ink.eccentric.domain.vo.common.Result;

import java.util.List;
import java.util.Map;

/**
 * @author eccentric
 */
public interface ArticleService{

    /**
     * 获取热门文章
     * @return
     */
    public Result hotArticleList();

    /**
     * 获取文章列表(分页)
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    public Result articleList(Integer pageNum, Integer pageSize, Long categoryId, Long tagId,String title,String summary);

    /**
     * 获取文章列表(分页)的数量
     * @param categoryId
     * @param tagId
     * @param title
     * @param summary
     * @return
     */
    public Integer articleListCount(Long categoryId, Long tagId,String title,String summary);

    /**
     * 获取文章详细信息
     * @param id
     * @return
     */
    public Result articleDetail(Long id);

    /**
     * 获取文章的浏览量
     * @return
     */
    public Map<String,Integer> getViewCounts();


    /**
     * 更新浏览量
     * @param id
     * @return
     */
    public Result updateViewCount(Long id);

    /**
     * 将redis中的数据同步到mysql
     */
    public void syncViewCount();

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
    public Result pigeonholeList(Integer pageNum, Integer pageSize);

    /**
     * 获取该文章的前一篇文章与后一篇文章
     * @param id
     * @return
     */
    public Result aroundArticle(Long id);

    /**
     * 添加文章
     * @param articleDto
     * @return
     */
    public Result addArticle(ArticleDto articleDto);

    /**
     * 修改文章
     * @param articleDto
     * @return
     */
    public Result updateArticle(ArticleDto articleDto);

    /**
     * 删除文章
     * @param idList
     * @return
     */
    public Result deleteArticle(List<Long> idList);
}
