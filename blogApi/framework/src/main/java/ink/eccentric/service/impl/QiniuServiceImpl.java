package ink.eccentric.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import ink.eccentric.enums.AppHttpCodeEnum;
import ink.eccentric.exception.SystemException;
import ink.eccentric.service.QiniuService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author eccentric
 */
@Service
public class QiniuServiceImpl implements QiniuService {
    //基本信息
    private String domain;
    private String accessKey;
    private String secretKey;
    private String bucketName;
    //测试域名

    //七牛文件上传管理器
    private UploadManager uploadManager;
    //七牛认证管理
    private Auth auth;
    //图片后缀
    private List<String> imgSuffix = Arrays.asList(".png",".bmp",".jpg",".jpeg",".pdf",".svg");



    @Override
    public void init() {
        ResourceBundle bundle = ResourceBundle.getBundle("qiniu");
        //设置密钥
        this.domain = bundle.getString("qiniu.domain");
        this.accessKey = bundle.getString("qiniu.accessKey");
        this.secretKey = bundle.getString("qiniu.secretKey");
        this.bucketName = bundle.getString("qiniu.bucketName");

        //密钥配置
        this.auth = Auth.create(this.accessKey,this.secretKey);
        this.uploadManager = new UploadManager(new Configuration(Zone.huanan()));
    }

    @Override
    public String uploadQiniuImg(MultipartFile file, String path) throws IOException {
        //初始化操作
        this.init();
        //文件名称
        String fileName = file.getOriginalFilename();
        //小数点索引
        int index = fileName.lastIndexOf(".");
        if (index == -1){
            throw new SystemException(AppHttpCodeEnum.IMAGE_ERROR);
        }
        // 获取文件后缀
        String suffix = fileName.substring(index);
        //判断文件是否为图片
        if (!imgSuffix.contains(suffix.toLowerCase())){
            throw new SystemException(AppHttpCodeEnum.IMAGE_ERROR);
        }

        // 生成上传至云服务器的文件名
        String uploadFileName = path + LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() + suffix;

        //调用put方法上传
        Response res = uploadManager.put(file.getBytes(), uploadFileName,auth.uploadToken(this.bucketName));

        //查看是否成功
        if (res.isOK() && res.isJson()){
            // 返回图片地址
            JSONObject jsonObject = JSON.parseObject(res.bodyString(), JSONObject.class);
            return this.domain + "/" + jsonObject.getString("key");
        }else{
            throw new SystemException(AppHttpCodeEnum.QINIU_ERROR);
        }
    }
}
