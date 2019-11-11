package com.cc.ata.common.config;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author :cc
 * @date : 2019/6/10
 */
public class MyExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        if (e instanceof UnauthorizedException) {
            return new ModelAndView("error/error-403");
        }
        if (e instanceof AuthorizationException) {
            return new ModelAndView("login");
        }
        if (e instanceof RuntimeException) {
            return new ModelAndView("error/500");
        }
        return null;
    }
}