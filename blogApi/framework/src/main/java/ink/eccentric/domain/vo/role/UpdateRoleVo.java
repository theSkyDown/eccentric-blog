package ink.eccentric.domain.vo.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author eccentric
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleVo {
    private Long id;
    private String remark;
    private String roleKey;
    private String roleName;
    private Integer roleSort;
    private String status;
    private List<Long> menuIds;
}
