package ink.eccentric.domain.vo.common;

import ink.eccentric.enums.AppHttpCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author eccentric
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result {

    private Integer code;

    private String message;

    private Integer count;

    private Object data;

    public static Result ok(){
        Result result = new Result();
        result.setCode(AppHttpCodeEnum.SUCCESS.getCode());
        result.setMessage(AppHttpCodeEnum.SUCCESS.getMsg());
        return result;
    }

    public static Result ok(Object obj){
        Result result = new Result();
        result.setCode(AppHttpCodeEnum.SUCCESS.getCode());
        result.setMessage(AppHttpCodeEnum.SUCCESS.getMsg());
        result.setData(obj);
        return result;
    }

    public static Result fail(){
        Result result = new Result();
        result.setCode(AppHttpCodeEnum.FAIL.getCode());
        result.setMessage(AppHttpCodeEnum.FAIL.getMsg());
        return result;
    }

    public static Result fail(Integer code, String message){
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public  Result message(String msg){
        this.setMessage(msg);
        return this;
    }
    public  Result message(Integer code){
        this.setCode(code);
        return this;
    }
    public  Result count(Integer count){
        this.setCount(count);
        return this;
    }

    public static Result paramsError(){
        Result result = new Result();
        result.code = AppHttpCodeEnum.PARAMS_FAIL.getCode();
        result.message = AppHttpCodeEnum.PARAMS_FAIL.getMsg();
        return result;
    }

    public static Result loginError(){
        Result result = new Result();
        result.code = AppHttpCodeEnum.LOGIN_ERROR.getCode();
        result.message = AppHttpCodeEnum.LOGIN_ERROR.getMsg();
        return result;
    }

}
