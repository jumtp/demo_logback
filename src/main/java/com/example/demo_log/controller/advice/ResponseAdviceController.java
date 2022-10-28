package com.example.demo_log.controller.advice;


import com.example.demo_log.anno.NotControllerResponseAdvice;
import com.example.demo_log.exception.APIException;
import com.example.demo_log.vo.ResultCode;
import com.example.demo_log.vo.ResultVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import java.lang.reflect.Type;

@RestControllerAdvice
public class ResponseAdviceController implements ResponseBodyAdvice<Object> {

    private final Logger log = LoggerFactory.getLogger(ResponseAdviceController.class);
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.getParameterType().isAssignableFrom(ResultVo.class)||
                returnType.hasMethodAnnotation(NotControllerResponseAdvice.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Type type = returnType.getGenericParameterType();
        if (type.equals(String.class) || type.equals(Object.class)) {
            try {
                // 将数据包装在ResultVo里后转换为json串进行返回
                return objectMapper.writeValueAsString(new ResultVo(body));
            } catch (JsonProcessingException e) {
                throw new APIException(ResultCode.RESPONSE_PACK_ERROR, e.getMessage());
            }
        }

        ResultVo resultVo = new ResultVo(body);
        log.info("返回包装数据 : {}" , resultVo);
        return resultVo;
    }
}