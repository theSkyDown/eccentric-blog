package ink.eccentric.domain.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author eccentric
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(description = "修改用户信息实体类")
public class UpdateUserInfoDto {
    //主键
    @ApiModelProperty("id")
    private Long id;
    //昵称
    @ApiModelProperty("昵称")
    private String nickName;
    //邮箱
    @ApiModelProperty("邮箱")
    private String email;
    //用户性别（0男，1女，2未知）
    @ApiModelProperty("用户性别（0男，1女，2未知）")
    private String sex;
    //头像
    @ApiModelProperty("头像")
    private String avatar;
}
