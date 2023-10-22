package ink.eccentric.controller;

import ink.eccentric.annotation.SystemLog;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eccentric
 */
@RestController
@RequestMapping("/link")
@Api(tags = "友链",description = "友链相关接口")
public class LinkController extends BaseController{

    @Autowired
    private LinkService linkService;

    /**
     * 获取所有友链信息
     * @return
     */
    @GetMapping("/list")
    @SystemLog(businessName = "获取所有友链信息")
    @ApiOperation(value = "获取所有友链信息",notes = "获取所有友链信息")
    public Result list(){
        return linkService.getAllLink();
    }

}
