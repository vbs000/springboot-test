package com.nxy.security.filte;

import com.nxy.jwt.JwtUtils;
import com.nxy.security.detail_service.CustomerUserDetailsService;
import com.nxy.security.handler.LoginFailureHandler;
import com.nxy.security.image_code.ImageCodeException;
import com.nxy.system.user.controller.UserController;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@Component("checkTokenFilter")
public class CheckTokenFilter extends OncePerRequestFilter {

    @Value("${nxy.loginUrl}")
    private String loginUrl;

    @Value("${nxy.imgUrl}")
    private String imgUrl;

    @Autowired
    private LoginFailureHandler loginFailureHandler;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        if(url.equals(loginUrl)){
            try{
                validate(request);
            }catch (AuthenticationException e){
                loginFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }else{//非登录，验证token
            //验证码请求不需要验证token
            String validateUrl = request.getRequestURI();
            if(!validateUrl.equals(this.imgUrl)){
                try{
                    validateToken(request);
                }catch (AuthenticationException e){
                    loginFailureHandler.onAuthenticationFailure(request,response,e);
                    return;
                }
            }

        }
        filterChain.doFilter(request,response);
    }
    //验证token
    private void validateToken(HttpServletRequest request){
        //获取前端传来的token
        String token = request.getHeader("token");
        Enumeration<String> headerNames = request.getHeaderNames();
        System.out.println("headers:");
        while(headerNames.hasMoreElements()){
            System.out.println(headerNames.nextElement());
        }
        //解析token，获取用户名
        String username = jwtUtils.getUsernameFromToken(token);
        //如果token为空，或者查询token中的用户名不存在的话
        if(StringUtils.isBlank(token) || StringUtils.isBlank(username)){
            throw new ImageCodeException("token验证失败！");
        }
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
        if(userDetails == null){
            throw new ImageCodeException("token验证失败！");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //设置为已登录
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    //验证验证码
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
