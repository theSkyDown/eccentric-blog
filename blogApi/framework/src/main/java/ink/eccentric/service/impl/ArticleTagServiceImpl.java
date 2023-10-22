package ink.eccentric.service.impl;

import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.mapper.ArticleTagMapper;
import ink.eccentric.service.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author eccentric
 */
@Service
public class ArticleTagServiceImpl implements ArticleTagService {

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public Integer addArticleTag(Long id, List<Long> tags) {
        return articleTagMapper.addArticleTag(id,tags);
    }

    @Override
    public List<Long> getTagsByArticleId(Long articleId) {
        return articleTagMapper.getTagsByArticleId(articleId);
    }

    @Override
    public Integer deleteAllArticleTagByArticleId(Long articleId) {
        return articleTagMapper.deleteAllArticleTagByArticleId(articleId);
    }
}
