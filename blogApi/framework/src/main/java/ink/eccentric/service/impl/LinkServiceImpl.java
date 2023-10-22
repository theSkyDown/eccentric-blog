package ink.eccentric.service.impl;

import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.mapper.LinkMapper;
import ink.eccentric.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author eccentric
 */
@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    @Override
    public Result getAllLink() {
        return Result.ok(linkMapper.getAllLink());
    }
}
