package com.nxy.system.permission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nxy.system.permission.entity.Permission;

import java.util.List;
public interface PermissionService extends IService<Permission> {
    /**
     * 根据用户Id查询所有的权限
     * @param userId
     * @return
     */
    List<Permission> getPermissionListByUserId(Long userId);
    /**
     * 根据角色id查询所有的权限
     * @param roleId
     * @return
     */
    List<Permission> getPermissionListByRoleId(Long roleId);
}