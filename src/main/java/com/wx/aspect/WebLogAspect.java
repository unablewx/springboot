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

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author:Mr.Wang
 * @Date: 2023/2/12  20:10
 * @Version 1.0
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class WebLogAspect {

    @Pointcut("execution(* com.wx.controller.*.*(..))")
    public void logPrint() {
    }

    @Before("logPrint()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        log.info("请求地址：{}", request.getRequestURI().toString());
        log.info("请求类型：{}", request.getMethod());
        log.info("请求方法 METHOD：{},{}", joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName());
        log.info("请求参数：{}", JsonUtil.objectToJson(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "logPrint()", returning = "ret")
    public void doAfterReturn(Object ret) {
        log.info("返回值: {}", JsonUtil.objectToJson(ret));
    }

    @Around("logPrint()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = point.proceed();
        log.info("Controller 方法耗时：{} ms", System.currentTimeMillis() - start);
        return proceed;
    }
}
