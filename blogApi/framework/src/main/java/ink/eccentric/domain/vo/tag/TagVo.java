package ink.eccentric.domain.vo.tag;

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
public class TagVo {
    private Long id;
    //标签名
    private String name;
    //备注
    private String remark;
}
