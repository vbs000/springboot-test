package com.nxy.security.filte;

import com.nxy.security.handler.LoginFailureHandler;
import com.nxy.security.image_code.ImageCodeException;
import com.nxy.system.user.controller.UserController;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component("checkTokenFilter")
public class CheckTokenFilter extends OncePerRequestFilter {

    @Value("${nxy.loginUrl}")
    private String loginUrl;
    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        if(url.equals(loginUrl)){
            try{
                validate(request);
            }catch (AuthenticationException e){
                loginFailureHandler.onAuthenticationFailure(request,response,e);
            }
        }
        filterChain.doFilter(request,response);
    }
    private void  validate(HttpServletRequest request){
        //1.获取登录请求的验证码
        String inputCode = request.getParameter("code");
        //2.获取Session中的验证码
        String sessionCode = (String) request.getSession().getAttribute(UserController.IMAGE_CODE_SESSION_KEY);
        //3.判断验证码是否为空
        if(StringUtils.isBlank(inputCode)){
            throw new ImageCodeException("验证码不能为空!");
        }
        //4.判断验证码是否相等
        if(!inputCode.equalsIgnoreCase(sessionCode)){
            throw new ImageCodeException("验证码输入错误!");
        }
    }
}
