package ink.eccentric.domain.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 后台用户的详细信息
 * @author eccentric
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminUserInfoVo {
    //权限
    private List<String> permissions;
    //角色
    private List<String> roles;
    //用户其他信息
    private UserInfoVo user;
}
