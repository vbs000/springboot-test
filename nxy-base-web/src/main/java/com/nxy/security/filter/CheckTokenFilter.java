//package com.nxy.security.filter;
//
//import com.nxy.jwt.JwtUtils;
//import com.nxy.security.detail_service.CustomerUserDetailsService;
//import com.nxy.security.exception.TokenException;
//import com.nxy.security.handler.LoginFailureHandler;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Slf4j
//@Component("checkTokenFilter")
//public class CheckTokenFilter extends OncePerRequestFilter {
//
//    @Value("${nxy.loginUrl}")
//    private String loginUrl;
//
//    @Autowired
//    private LoginFailureHandler loginFailureHandler;
//    @Autowired
//    private JwtUtils jwtUtils;
//    @Autowired
//    private CustomerUserDetailsService customerUserDetailsService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String url = request.getRequestURI();
//        //validateToken(request);
//        filterChain.doFilter(request,response);
//    }
//
//    //验证token
//    private void validateToken(HttpServletRequest request){
//        //获取前端传来的token
//        String token = request.getHeader("token");
//        //解析token，获取用户名
//        String username = jwtUtils.getUsernameFromToken(token);
//        //如果token或者用户名为空的话，不能通过认证
//        if(StringUtils.isBlank(token) || StringUtils.isBlank(username)){
//            throw new TokenException("token验证失败!");
//        }
//        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
//        if(userDetails == null){
//            throw new TokenException("token验证失败!");
//        }
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        //设置为已登录
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//    }
//}
