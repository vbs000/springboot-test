package com.nxy.system.user.service;

import ch.qos.logback.core.util.SystemInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nxy.system.user.entity.SysUser;

/**
 * 用户Service
 */
public interface UserService extends IService<SysUser> {
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    SysUser getUserByUserName(String username);

}
