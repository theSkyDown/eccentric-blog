package ink.eccentric.mapper;

import ink.eccentric.domain.vo.link.LinkVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author eccentric
 */
@Mapper
public interface LinkMapper {


    /**
     * 获取所有友链
     * @return
     */
    public List<LinkVo> getAllLink();
}
