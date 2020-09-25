package com.nxy.system.permission.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nxy.system.permission.entity.Permission;
import com.nxy.system.permission.mapper.PermissionMapper;
import com.nxy.system.permission.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper,Permission> implements PermissionService {
    @Override
    // @Cacheable(value = "permissions",key = "#userId")
    public List<Permission> getPermissionListByUserId(Long userId) {
        return this.baseMapper.getPermissionListByUserId(userId);
    }
    @Override
    // @Cacheable(value = "permissions",key = "#roleId")
    public List<Permission> getPermissionListByRoleId(Long roleId) {
        return this.baseMapper.getPermissionListByRoleId(roleId);
    }
}
