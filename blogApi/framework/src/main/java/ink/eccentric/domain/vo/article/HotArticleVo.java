package ink.eccentric.domain.vo.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 热门文章vo
 * @author eccentric
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HotArticleVo {
    private Long id;
    //标题
    private String title;
    //访问量
    private Long viewCount;
}
