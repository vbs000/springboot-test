package com.nxy.system.permission.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nxy.result.ResultUtils;
import com.nxy.result.ResultVo;
import com.nxy.system.permission.entity.Permission;
import com.nxy.system.permission.service.PermissionService;
import com.nxy.system.permission.vo.MakeMenuTree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    /**
     * 根据ID删除权限
     * @return
     */
    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    public ResultVo deleteById(@RequestBody Permission permission){
        boolean result = permissionService.removeById(permission);
        if(result){
            return ResultUtils.success("删除权限成功！");
        }else{
            return ResultUtils.error("删除权限失败！");
        }
    }
    /**
     * 编辑权限保存
     * @return
     */
    @RequestMapping(value = "/editSave",method = RequestMethod.POST)
    public ResultVo editSave(@RequestBody Permission permission){
        boolean result = permissionService.updateById(permission);
        if(result){
            return ResultUtils.success("编辑权限成功！");
        }else{
            return ResultUtils.error("编辑权限失败！");
        }
    }


    /**
     * 根据ID查询编辑的权限
     * @return
     */
    @RequestMapping(value = "/getPermissionById",method = RequestMethod.POST)
    public ResultVo getPermissionById(@RequestBody Permission permission){
        Permission res = permissionService.getById(permission.getId());
        return ResultUtils.success("查询成功！",res);
    }

    /**
     * 新增权限
     * @return
     */
    @RequestMapping(value = "/addPermission",method = RequestMethod.POST)
    public ResultVo addPermission(@RequestBody Permission permission){
        boolean result = permissionService.save(permission);
        if(result){
            return ResultUtils.success("新增权限成功！");
        }else{
            return ResultUtils.error("新增权限失败！");
        }
    }

    /**
     * 获取权限列表数据
     * @return
     */
    @RequestMapping(value = "/getMenuList",method = RequestMethod.POST)
    public ResultVo getMenuList(){
        QueryWrapper<Permission> query = new QueryWrapper<>();
        query.lambda().orderByAsc(Permission::getOrderNum);
        List<Permission> list = permissionService.list();
        List<Permission> permissions = list.stream().filter(item -> item != null && !"2".equals(item.getType())).collect(Collectors.toList());
        List<Permission> menuList = null;
        if(permissions != null && permissions.size() > 0){
            menuList = MakeMenuTree.makeTree(permissions,0L);
        }
        return ResultUtils.success("查询列表成功",menuList);
    }

}
