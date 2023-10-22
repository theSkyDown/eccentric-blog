package ink.eccentric.domain.vo.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * (归档)文章列表Vo
 * @author eccentric
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PigeonholeArticleVo {
    private Long id;
    //标题
    private String title;
    //文章内容
    private String summary;
    //缩略图
    private String thumbnail;
    @JsonFormat(shape= JsonFormat.Shape.STRING ,pattern="yyyy-MM-dd")
    private Date createTime;
}
