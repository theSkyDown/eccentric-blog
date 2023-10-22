package ink.eccentric.domain.vo.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 文章详细信息
 * @author eccentric
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleDetailVo {
    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //所属分类id
    private Long categoryId;
    //缩略图
    private String thumbnail;
    //分类名称
    private String categoryName;
    //访问量
    private Long viewCount;
    //是否允许评论 1是，0否
    private String isComment;

    @JsonFormat(shape= JsonFormat.Shape.STRING ,pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    //标签
    private List<Long> tags;
    //文章摘要
    private String summary;
    //是否置顶（0否，1是）
    private String isTop;
}
