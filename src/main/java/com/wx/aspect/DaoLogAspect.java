package com.wx.aspect;

import com.wx.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:Mr.Wang
 * @Date: 2023/2/12  21:46
 * @Version 1.0
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class DaoLogAspect {
    @Pointcut("execution(* com.wx.mapper.*Mapper.*(..))")
    public void logPrint() {
    }


    @Around("logPrint()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();
        log.info("数据库开始执行：{}",point.getSignature().toShortString());
        Object proceed = point.proceed();
        log.info("数据库执行耗时：{} ms", System.currentTimeMillis() - start);
        return proceed;
    }
}
