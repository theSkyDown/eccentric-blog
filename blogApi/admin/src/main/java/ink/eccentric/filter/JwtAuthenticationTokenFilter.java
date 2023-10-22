package ink.eccentric.filter;

import com.alibaba.fastjson.JSON;
import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.domain.vo.common.UserDetailsImpl;
import ink.eccentric.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author eccentric
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)){
            filterChain.doFilter(request,response);
            return;
        }
        //解析token,获取userId的值
        Long userId;
        try{
            Claims claims = JwtUtil.parseJwt(token);
            userId = claims.get("userId",Long.class);
        }catch (Exception e){
            response.getWriter().write(JSON.toJSONString(Result.fail().message("token is not available")));
            return;
        }
        //获取用户信息
        String userJson = redisTemplate.opsForValue().get("AdminLogin:" + userId);
        UserDetailsImpl userDetails = JSON.parseObject(userJson, UserDetailsImpl.class);
        if (Objects.isNull(userDetails)){
            response.getWriter().write(JSON.toJSONString(Result.fail().message("用户登陆过期")));
            return;
        }
        //存入SecurityContextHolder中
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request,response);
    }
}
