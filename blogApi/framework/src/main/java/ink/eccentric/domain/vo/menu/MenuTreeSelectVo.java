package ink.eccentric.domain.vo.menu;

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
public class MenuTreeSelectVo {
    private Long id;
    private List<MenuTreeSelectVo> children;
    private String label;
    private Long parentId;
}
