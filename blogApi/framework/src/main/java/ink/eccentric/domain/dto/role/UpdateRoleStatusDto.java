package ink.eccentric.domain.dto.role;

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
public class UpdateRoleStatusDto {
    private Long id;
    private String status;
}
