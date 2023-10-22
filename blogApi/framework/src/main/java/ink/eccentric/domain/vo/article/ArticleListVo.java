package ink.eccentric.domain.vo.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author eccentric
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleListVo {
    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    //访问量
    private Long viewCount;

    @JsonFormat(shape= JsonFormat.Shape.STRING ,pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //缩略图
    private String thumbnail;
    //分类名称
    private String categoryName;
}
