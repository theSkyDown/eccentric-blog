package ink.eccentric.domain.vo.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * 文章粗略信息
 * @author eccentric
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleRoughVo {

    private Long id;
    //标题
    private String title;
    //缩略图
    private String thumbnail;

}
