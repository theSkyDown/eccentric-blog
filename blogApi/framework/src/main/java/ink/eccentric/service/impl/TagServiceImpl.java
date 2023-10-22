package ink.eccentric.service.impl;

import ink.eccentric.domain.dto.tag.AddTagDto;
import ink.eccentric.domain.dto.tag.UpdateTagDto;
import ink.eccentric.domain.po.Tag;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.domain.vo.tag.TagVo;
import ink.eccentric.mapper.TagMapper;
import ink.eccentric.service.TagService;
import ink.eccentric.utils.BeanCopyUtil;
import ink.eccentric.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author eccentric
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Result getTagList() {
        return Result.ok(tagMapper.getTagList(null,null,null,null));
    }

    @Override
    public Integer getTagCount() {
        return tagMapper.getTagCount(null,null);
    }

    @Override
    public Result getTagPagesList(Integer pageNum, Integer pageSize, String name, String remark) {
        Map<String,Object> res = new HashMap<>();
        List<TagVo> tags = tagMapper.getTagList(pageNum-1,pageSize,name,remark);
        res.put("rows",tags);
        Integer count = tagMapper.getTagCount(name,remark);
        res.put("total",count);
        return Result.ok(res);
    }

    @Override
    public Result addTag(AddTagDto addTagDto) {
        Tag tag = BeanCopyUtil.copyBean(addTagDto, Tag.class);
        //创建人
        tag.setCreateBy(SecurityUtil.getUser().getId());
        //更新人
        tag.setUpdateBy(SecurityUtil.getUser().getId());
        //创建时间
        tag.setCreateTime(new Date());
        //更新时间
        tag.setUpdateTime(new Date());
        //封装
        return tagMapper.insertTag(tag)==1?Result.ok():Result.fail();
    }

    @Override
    public Result deleteTag(List<Long> ids) {
        return tagMapper.deleteTag(ids,SecurityUtil.getUser().getId(),new Date())>0?Result.ok():Result.fail();
    }


    @Override
    public Result getTagById(Long id) {
        return Result.ok(tagMapper.getTagById(id));
    }

    @Override
    public Result updateTag(UpdateTagDto updateTagDto) {
        Tag tag = BeanCopyUtil.copyBean(updateTagDto, Tag.class);
        //更新人
        tag.setUpdateBy(SecurityUtil.getUser().getId());
        //更新时间
        tag.setUpdateTime(new Date());
        return tagMapper.updateTag(tag)==1?Result.ok():Result.fail();
    }
}
