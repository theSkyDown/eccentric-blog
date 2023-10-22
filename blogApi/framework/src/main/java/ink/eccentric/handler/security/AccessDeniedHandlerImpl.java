package ink.eccentric.handler.security;

import com.alibaba.fastjson.JSON;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.enums.AppHttpCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author eccentric
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Result result = Result.fail(AppHttpCodeEnum.NO_OPERATOR_AUTH.getCode(), AppHttpCodeEnum.NO_OPERATOR_AUTH.getMsg());
        //处理异常
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(JSON.toJSONString(result));
    }
}
