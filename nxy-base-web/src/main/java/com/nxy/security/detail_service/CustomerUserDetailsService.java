package com.nxy.security.detail_service;

import com.nxy.system.permission.entity.Permission;
import com.nxy.system.permission.service.PermissionService;
import com.nxy.system.user.entity.SysUser;
import com.nxy.system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("customerUserDetailsService")
public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.从数据库查询用户是否存在
        SysUser user = userService.getUserByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        //2.查询用户所有的权限
        List<Permission> codeList = permissionService.getPermissionListByUserId(user.getId());
        //3.获取权限code字段
        List<String> collect = codeList.stream().filter(item-> item !=null).map(item -> item.getCode()).collect(Collectors.toList());
        //4.把code交给Spring Security
        String[] codeArr = collect.toArray(new String[collect.size()]);
        //5.把code转成List<GrantedAuthority>
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(codeArr);
        user.setAuthorities(authorityList);
        //6.把菜单封装到user
        user.setPermissionList(codeList);
        return user;
    }
}
