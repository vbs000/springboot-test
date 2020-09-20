package com.nxy.security.detail_service;

import com.nxy.system.user.entity.SysUser;
import com.nxy.system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("customerUserDetailsService")
public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.从数据库查询用户是否存在
        SysUser user = userService.getUserByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        //2.
        return user;
    }
}
