package ink.eccentric.mapper;


import ink.eccentric.domain.dto.tag.UpdateTagDto;
import ink.eccentric.domain.po.Tag;
import ink.eccentric.domain.vo.tag.TagVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author eccentric
 */
@Mapper
public interface TagMapper {

    /**
     * 获取所有标签
     * @return
     */
    public List<TagVo> getTagList(@Param("pageNum") Integer pageNum,@Param("pageSize") Integer pageSize,@Param("name") String name,@Param("remark") String remark);

    /**
     * 获取标签数量
     * @return
     */
    public Integer getTagCount(@Param("name") String name,@Param("remark") String remark);

    /**
     * 添加标签
     * @param tag
     * @return
     */
    public Integer insertTag(Tag tag);

    /**
     * 删除标签
     * @param ids
     * @return
     */
    public Integer deleteTag(@Param("ids") List<Long> ids, @Param("updateBy") Long updateBy,@Param("updateTime") Date updateTime);

    /**
     * 获取指定标签信息
     * @param id
     * @return
     */
    public TagVo getTagById(Long id);

    /**
     * 修改标签
     * @param tag
     * @return
     */
    public Integer updateTag(Tag tag);
}
