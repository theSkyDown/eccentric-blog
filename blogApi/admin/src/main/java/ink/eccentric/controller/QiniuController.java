package ink.eccentric.controller;

import ink.eccentric.annotation.SystemLog;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.enums.AppHttpCodeEnum;
import ink.eccentric.exception.SystemException;
import ink.eccentric.service.QiniuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * 七牛上传文件
 * @author eccentric
 */
@RestController
@RequestMapping("/qiniu")
@Api(tags = "七牛",description = "七牛上传文件相关接口")
public class QiniuController extends BaseController{

    @Autowired
    private QiniuService qiniuService;

    private static final String STR_DATE_FORMAT = "yyyy-MM-dd/";

    /**
     * 上传文章图片
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/article")
    @SystemLog(businessName = "上传文章图片")
    @ApiOperation(value = "上传文章图片",notes = "上传文章图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file",value = "图片文件")
    })
    public Result avatarUpload(@RequestParam("file") MultipartFile file) throws IOException {
        //拦截空文件
        if (file.isEmpty()){
            return Result.fail(AppHttpCodeEnum.IMAGE_ERROR.getCode(),AppHttpCodeEnum.IMAGE_ERROR.getMsg());
        }
        SimpleDateFormat sdf = new SimpleDateFormat(STR_DATE_FORMAT);
        String fileUrl = qiniuService.uploadQiniuImg(file,"article/" + sdf.format(new Date()));
        return Objects.nonNull(fileUrl)?Result.ok(fileUrl):Result.fail().message("上传失败");
    }

}
