package ink.eccentric.enums;

/**
 * @author eccentric
 */

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    //失败
    FAIL(400,"操作失败"),
    PARAMS_FAIL(400,"参数异常"),
    ARTICLE_NOT_FOUND(400,"文章不存在"),
    CONTENT_NULL(400,"内容为空"),
    IMAGE_ERROR(400,"图片异常"),
    QINIU_ERROR(400,"七牛异常"),
    USER_NOT_EXIST(400,"用户不存在"),
    // 登录
    NEED_LOGIN(401,"请登陆后再尝试"),
    AUTHENTICATE_FAIL(401,"认证失败"),
    NO_OPERATOR_AUTH(403,"权限不足"),
    SYSTEM_ERROR(400,"出现错误"),
    USERNAME_EXIST(400,"用户名已存在"),
    NICKNAME_EXIST(400,"昵称已存在"),
    PHONENUMBER_EXIST(400,"手机号已存在"),
    EMAIL_EXIST(400, "邮箱已存在"),

    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(401,"用户名或密码错误");
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}