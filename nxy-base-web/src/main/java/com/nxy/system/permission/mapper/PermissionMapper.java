package com.nxy.system.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nxy.system.permission.entity.Permission;

import java.util.List;

/**
 * 权限mapper接口
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 根据用户ID查询用户权限
     * @param userId
     * @return
     */
    List<Permission> getPermissionListByUserId(Long userId);

    /**
     * 根据角色ID查询权限列表
     * @param id
     * @return
     */
    List<Permission> getPermissionListByRoleId(Long id);

}
