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
public class AdminLoginUserVo {

    private String token;
}
