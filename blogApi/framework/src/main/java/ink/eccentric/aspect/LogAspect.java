package ink.eccentric.aspect;

import com.alibaba.fastjson.JSON;
import ink.eccentric.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author eccentric
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(ink.eccentric.annotation.SystemLog)")
    public void pointcut(){

    }

    @Around("pointcut()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            handleBefore(joinPoint);
            result = joinPoint.proceed();
            handleAfter(result);
        }finally {
            log.info("================End================");
        }

        return result;
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {
        log.info("===============Start===============");
        //请求 URL
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        log.info("URL              : {}",request.getRequestURL());
        //描述信息
        log.info("{}",joinPoint.getTarget().getClass());
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        SystemLog annotation = signature.getMethod().getAnnotation(SystemLog.class);
        log.info("BusinessName     : {}",annotation.businessName());
        // method 方法头
        log.info("HTTP Method      : {}",request.getMethod());
        //执行的 Controller 方法
        log.info("Class Method     : {}.{}",signature.getDeclaringTypeName(),signature.getName());
        //请求的 Ip
        log.info("IP               : {}",request.getRemoteHost());
        //传入参数
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof MultipartFile){
                MultipartFile file = (MultipartFile) arg;
                log.info("Request Params   : {}","文件:"+file.getOriginalFilename());
                return;
            }
        }
        log.info("Request Params   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private void handleAfter(Object result) {
        //打印结果
        log.info("Response         : {}",JSON.toJSONString(result));
    }
}
