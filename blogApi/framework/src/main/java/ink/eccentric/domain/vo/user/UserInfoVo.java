package ink.eccentric.domain.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author eccentric
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInfoVo {
    private Long id;
    //头像
    private String avatar;
    //邮箱
    private String email;
    //昵称
    private String nickName;
    //用户性别（0男，1女，2未知）
    private String sex;
}