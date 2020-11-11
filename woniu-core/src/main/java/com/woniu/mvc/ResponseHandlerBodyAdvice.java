package com.woniu.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class ResponseHandlerBodyAdvice implements ResponseBodyAdvice {

    private static Logger log = LoggerFactory.getLogger(ResponseHandlerBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        //统一异常处理的返回值会经过此方法
        if (body instanceof Results) {
            return body;
        }
        //统一返回值的格式
        if (request.getURI().getPath().startsWith("/api/")) {
            return Results.success(body);
        }
        return body;
    }

    /**
     * 统一异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Object handle(Exception e) {
        log.error("系统异常", e);
        return Results.error(e.getMessage());
    }
}
