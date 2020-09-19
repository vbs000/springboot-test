package com.nxy.system.user.controller;

import com.nxy.result.ResultUtils;
import com.nxy.result.ResultVo;
import com.nxy.system.user.entity.SysUser;
import com.nxy.system.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getList")
    public ResultVo getList(){
        List<SysUser> list = userService.list();
        return ResultUtils.success("查询成功",list);
    }
}
