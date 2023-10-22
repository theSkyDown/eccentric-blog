package ink.eccentric.domain.dto.tag;

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
public class UpdateTagDto {
    private Long id;
    //标签名称
    private String name;
    //备注
    private String remark;
}
