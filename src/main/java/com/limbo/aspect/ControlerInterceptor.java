package com.limbo.aspect;

import com.limbo.dto.Result;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * ControllerAOP 拦截除/login,/signUp,/user/exits,以外的请求
 * Created by limbo on 17-4-5.
 */
@Component
@Aspect
public class ControlerInterceptor {

    @Pointcut("execution(* com.limbo.controller..*(..))&&" +
            "!execution(* com.limbo.controller..userSignUp(..))&&" +
            "!execution(* com.limbo.controller..login(..))&&" +
            "!execution(* com.limbo.controller..userExists(..))"
    )
    public void controllerMethodPointcut(){}
    

    @Around("controllerMethodPointcut()")
    public Object Interceptor(ProceedingJoinPoint pjp){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new Result(1,"Missing or invalid Authorization header.",new Date().getTime(),null);
        }

        final String token = authHeader.substring(7); // The part after "Bearer "

        try {
            final Claims claims = Jwts.parser().setSigningKey("d76ebe22e95fa9da30fb401f3074cd8dcad4efc3")
                    .parseClaimsJws(token).getBody();
            request.setAttribute("claims", claims);
        }
        catch (final SignatureException e) {
            return new Result(2,"Invalid token.",new Date().getTime(),null);
        }catch (ExpiredJwtException e){
            return new Result(3,"overtime token",new Date().getTime(),null);
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return new Result(4," unknown error",new Date().getTime(),null);
        }
    }
}


