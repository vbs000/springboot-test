package com.nxy.system.user.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nxy.system.user.entity.SysUser;
import com.nxy.system.user.mapper.SysUserMapper;
import com.nxy.system.user.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户Service实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>  implements UserService {
}
