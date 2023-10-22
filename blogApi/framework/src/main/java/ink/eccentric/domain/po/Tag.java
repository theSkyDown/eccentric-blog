package ink.eccentric.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;

/**
 * 标签(Tag)表实体类
 *
 * @author makejava
 * @since 2023-07-11 13:04:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Tag {
    
    private Long id;
    //标签名
    private String name;
    
    private Long createBy;
    
    private Date createTime;
    
    private Long updateBy;
    
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
    //备注
    private String remark;
}

