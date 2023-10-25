package ink.eccentric.domain.dto.menu;

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
public class MenuDto {
    private Long id;
    private Long parentId;
    private String menuType;
    private String icon;
    private String menuName;
    private Integer orderNum;
    private String path;
    private String component;
    private String perms;
    private String visible;
    private String status;
}
