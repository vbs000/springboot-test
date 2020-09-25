package com.nxy.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.nxy.jwt.JwtUtils;
import com.nxy.result.ResultUtils;
import com.nxy.system.permission.entity.Permission;
import com.nxy.system.permission.vo.MakeMenuTree;
import com.nxy.system.permission.vo.MenuVo;
import com.nxy.system.user.entity.SysUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录认证成功处理器
 */
@Component("loginSuccessHandler")
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //1.生成token
        SysUser user = (SysUser)authentication.getPrincipal();
        String token = jwtUtils.generateToken(user);
        MenuVo vo = new MenuVo();
        vo.setToken(token);
        vo.setUserId(user.getId());
        //2.获取用户菜单权限
        List<Permission> permissionList = user.getPermissionList();
        //3.获取code字段，返回给前端使用，判断是否有按钮权限
        List<String> auth = permissionList.stream().filter(item -> item != null).map(item -> item.getCode()).collect(Collectors.toList());
        vo.setAuthList(auth);
        //4.生成菜单数据树
        List<Permission> permissions = permissionList.stream().filter(item -> item != null && item.getType() != "2").collect(Collectors.toList());
        List<Permission> menuList = MakeMenuTree.makeTree(permissions, 0L);
        vo.setMenuList(menuList);
        List<Permission> routeList = permissionList.stream().filter(item -> item != null && item.getType() == "1").collect(Collectors.toList());
        vo.setRouterList(routeList);
        //5.查询路由的url
        String res = JSONObject.toJSONString(ResultUtils.success("认证成功",vo), SerializerFeature.DisableCircularReferenceDetect);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write(res.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }


}