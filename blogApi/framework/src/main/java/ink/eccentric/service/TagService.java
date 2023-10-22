package ink.eccentric.service;

import ink.eccentric.domain.dto.tag.AddTagDto;
import ink.eccentric.domain.dto.tag.UpdateTagDto;
import ink.eccentric.domain.vo.common.Result;

import java.util.List;

/**
 * @author eccentric
 */
public interface TagService {

    /**
     * 获取所有标签
     * @return
     */
    public Result getTagList();

    /**
     * 获取标签数量
     * @return
     */
    public Integer getTagCount();

    /**
     * 获取标签列表通过分页的形式
     * @param pageNum
     * @param pageSize
     * @param name
     * @param remark
     * @return
     */
    public Result getTagPagesList(Integer pageNum, Integer pageSize, String name, String remark);

    /**
     * 添加标签
     * @param addTagDto
     * @return
     */
    public Result addTag(AddTagDto addTagDto);

    /**
     * 删除标签
     * @param id
     * @return
     */
    public Result deleteTag(List<Long> ids);

    /**
     * 获取标签信息通过id
     * @param id
     * @return
     */
    public Result getTagById(Long id);

    /**
     * 修改标签
     * @param updateTagDto
     * @return
     */
    public Result updateTag(UpdateTagDto updateTagDto);
}
