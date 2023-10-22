package ink.eccentric.domain.dto.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author eccentric
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(description = "发送评论实体类")
public class SendCommentDto {
    @ApiModelProperty("评论类型(0代表文章评论，1代表友链评论)")
    private String type;
    //文章id
    @ApiModelProperty("文章id")
    private Long articleId;
    //根评论id
    @ApiModelProperty("根评论id")
    private Long rootId;
    //评论内容
    @ApiModelProperty("评论内容")
    private String content;
    //所回复的目标评论的userid
    @ApiModelProperty("所回复的目标评论的userid")
    private Long toCommentUserId;
    //回复目标评论id
    @ApiModelProperty("回复目标评论id")
    private Long toCommentId;
}
