package com.nxy.system.permission.vo;

import com.nxy.system.permission.entity.Permission;
import lombok.Data;

import java.io.Serializable;

import java.util.List;

/**
 * 菜单返回实体
 */
public class MenuVo implements Serializable {
    private List<Permission> menuList;
    private List<String> authList;
    private List<Permission> routerList;
    private String token;
    private Long userId;

    public List<Permission> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Permission> menuList) {
        this.menuList = menuList;
    }

    public List<String> getAuthList() {
        return authList;
    }

    public void setAuthList(List<String> authList) {
        this.authList = authList;
    }

    public List<Permission> getRouterList() {
        return routerList;
    }

    public void setRouterList(List<Permission> routerList) {
        this.routerList = routerList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

