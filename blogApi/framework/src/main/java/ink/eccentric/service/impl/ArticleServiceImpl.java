package ink.eccentric.service.impl;

import ink.eccentric.constants.SystemConstants;
import ink.eccentric.domain.dto.article.ArticleDto;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.domain.po.Article;
import ink.eccentric.domain.vo.article.AroundArticleVo;
import ink.eccentric.domain.vo.article.ArticleDetailVo;
import ink.eccentric.enums.AppHttpCodeEnum;
import ink.eccentric.mapper.ArticleMapper;
import ink.eccentric.service.ArticleService;
import ink.eccentric.service.ArticleTagService;
import ink.eccentric.utils.BeanCopyUtil;
import ink.eccentric.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * @author eccentric
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public Result hotArticleList() {
        return Result.ok(articleMapper.hotArticleList());
    }

    @Override
    public Result articleList(Integer pageNum, Integer pageSize, Long categoryId,Long tagId,String title,String summary) {
        return Result.ok(articleMapper.articleList(pageNum-1,pageSize,categoryId,tagId,title,summary));
    }

    @Override
    public Integer articleListCount(Long categoryId, Long tagId,String title,String summary) {
        return articleMapper.articleListCount(categoryId,tagId,title,summary);
    }

    @Override
    public Result articleDetail(Long id) {
        ArticleDetailVo articleDetailVo = articleMapper.articleDetail(id);
        if (Objects.isNull(articleDetailVo)){
            return Result.fail(AppHttpCodeEnum.ARTICLE_NOT_FOUND.getCode(),AppHttpCodeEnum.ARTICLE_NOT_FOUND.getMsg());
        }
        articleDetailVo.setTags(articleTagService.getTagsByArticleId(id));
        return Result.ok(articleDetailVo);
    }

    @Override
    public Map<String, Integer> getViewCounts() {
        List<Article> viewCounts = articleMapper.getViewCounts();
        Map<String,Integer> result = new HashMap<>();
        for (Article viewCount : viewCounts) {
            result.put(viewCount.getId().toString(),viewCount.getViewCount().intValue());
        }
        return result;
    }

    @Override
    public Result updateViewCount(Long id) {
        //更新浏览量
        redisTemplate.opsForHash().increment("article:viewCount",id.toString(),1L);
        return Result.ok();
    }


    @Override
    public void syncViewCount() {
        //获取所有访问列表
        Map<String,Integer> viewCounts = redisTemplate.opsForHash().entries("article:viewCount");
        Map<Long,Long> viewCountMap = new HashMap<>();
        for (String item : viewCounts.keySet()) {
            viewCountMap.put(Long.valueOf(item),Long.valueOf(viewCounts.get(item)));
        }
        Integer count = articleMapper.updateViewCount(viewCountMap);
        if (count > 0){
            log.info("定时任务：访问量同步完成");
        }
    }

    @Override
    public Integer pigeonholeListCount() {
        return articleMapper.pigeonholeListCount();
    }

    @Override
    public Result pigeonholeList(Integer pageNum, Integer pageSize) {
        return Result.ok(articleMapper.pigeonholeList(pageNum-1,pageSize));
    }

    @Override
    public Result aroundArticle(Long id) {
        AroundArticleVo aroundArticleVo = new AroundArticleVo();
        aroundArticleVo.setBeforeArticle(articleMapper.getBeforeArticle(id));
        aroundArticleVo.setAfterArticle(articleMapper.getAfterArticle(id));
        return Result.ok(aroundArticleVo);
    }

    @Override
    @Transactional
    public Result addArticle(ArticleDto articleDto) {
        //需开启事务，先添加文章后在添加对应的标签至article_tag表
        Article article = BeanCopyUtil.copyBean(articleDto, Article.class);
        //设置一些其他信息，如创建时间、创建人、更新时间、更新人
        //创建人
        article.setCreateBy(SecurityUtil.getUser().getId());
        //更新人
        article.setUpdateBy(SecurityUtil.getUser().getId());
        //创建时间
        article.setCreateTime(new Date());
        //更新时间
        article.setUpdateTime(new Date());
        Integer addArticleCount = articleMapper.addArticle(article);
        //如果没添加成功，则回滚
        if (addArticleCount<1){
            //执行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail();
        }
        //添加对应的标签(article_tag)
        Integer count = articleTagService.addArticleTag(article.getId(), articleDto.getTags());
        if (count < 1) {
            //执行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail();
        }
        return Result.ok();
    }

    @Override
    @Transactional
    public Result updateArticle(ArticleDto articleDto) {
        //需开启事务，先修改文章再添加对应的标签至article_tag表(存在则跳过)
        Article article = BeanCopyUtil.copyBean(articleDto, Article.class);
        //设置一些其他信息，如创建时间、创建人、更新时间、更新人
        //更新人
        article.setUpdateBy(SecurityUtil.getUser().getId());
        //更新时间
        article.setUpdateTime(new Date());
        Integer updateArticleCount = articleMapper.updateArticle(article);
        //如果没修改成功，则回滚
        if (updateArticleCount<1){
            //执行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail();
        }
        //删除该文章所有的标签
        Integer deleteAllArticleTagCount = articleTagService.deleteAllArticleTagByArticleId(article.getId());
        if (deleteAllArticleTagCount < 1){
            //执行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail();
        }
        //重新添加对应的标签(article_tag)
        Integer addArticleTagCount = articleTagService.addArticleTag(article.getId(), articleDto.getTags());
        if (addArticleTagCount < 1) {
            //执行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail();
        }
        return Result.ok();
    }

    @Override
    public Result deleteArticle(List<Long> idList) {
        return articleMapper.deleteArticle(idList,SecurityUtil.getUser().getId(),new Date())>0?Result.ok():Result.fail();
    }
}
