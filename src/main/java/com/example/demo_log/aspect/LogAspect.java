package com.example.demo_log.aspect;

import com.example.demo_log.utils.FastIp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    private final Logger log = LoggerFactory.getLogger(LogAspect.class);

    private final ObjectMapper objectMapper;
    private final FastIp fastIp;
    private StopWatch stopWatch = new StopWatch();
    private Class clazz;
    private Method method;

    public LogAspect(ObjectMapper objectMapper, FastIp fastIp) {
        this.objectMapper = objectMapper;
        this.fastIp = fastIp;
    }

    @Pointcut("execution(public * com.example.demo_log.controller.*.*(..)))")
    private void aspect() {
    }

    @Before("aspect()")
    public void before(JoinPoint joinPoint) {
        stopWatch.start();
        clazz = joinPoint.getTarget().getClass();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        log.info("=======================请求内容=====================");
        String requestMethod = request.getMethod();
        log.info("请求IP:{}", fastIp.getIp(request));
        log.info("请求地址:{}", request.getRequestURI());
        log.info("请求方式:{}", requestMethod);
        log.info("请求类方法:{}", joinPoint.getSignature());
        log.info("请求类方法参数:{}", Arrays.toString(joinPoint.getArgs()));
        log.info("=======================请求内容 结束=======================");
    }

    @AfterReturning(returning = "obj", pointcut = "aspect()")
    public void methodAfterReturning(Object obj) {
        stopWatch.stop();
        log.info("执行时长 : {} ms", stopWatch.getLastTaskTimeMillis());
        log.info("=======================返回内容=======================");
        try {
            log.info(objectMapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("发生错误 : {}", e.toString());
        }
        log.info("=======================返回内容 结束=======================");
    }

}
