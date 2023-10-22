package ink.eccentric.domain.vo.article;

import ink.eccentric.domain.vo.article.ArticleRoughVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * 上一篇文章 和 下一篇文章
 * @author eccentric
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AroundArticleVo {

    //上一篇文章
    private ArticleRoughVo beforeArticle;

    //下一篇文章
    private ArticleRoughVo afterArticle;

}
