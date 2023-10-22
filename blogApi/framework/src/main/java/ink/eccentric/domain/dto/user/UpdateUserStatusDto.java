package ink.eccentric.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author eccentric
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserStatusDto {
    //主键
    private Long id;
    //状态
    private String status;

}
