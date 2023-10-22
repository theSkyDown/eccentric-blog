package ink.eccentric.domain.vo.category;

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
public class CategoryVo {
    private Long id;
    //分类名
    private String name;
    //描述
    private String description;
    //状态
    private String status;
}
