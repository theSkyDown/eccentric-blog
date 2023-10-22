package ink.eccentric.domain.vo.user;

import ink.eccentric.domain.vo.user.UserInfoVo;
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
public class LoginUserVo {

    private String token;
    private UserInfoVo userInfo;
}
