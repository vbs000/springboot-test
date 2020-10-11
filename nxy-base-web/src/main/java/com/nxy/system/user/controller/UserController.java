package com.nxy.system.user.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.nxy.result.ResultUtils;
import com.nxy.result.ResultVo;
import com.nxy.system.user.entity.SysUser;
import com.nxy.system.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * 用户管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    public static final String IMAGE_CODE_SESSION_KEY = "IMAGE_CODE";

    @Autowired
    private UserService userService;
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @RequestMapping("/getList")
    public ResultVo getList(){
        List<SysUser> list = userService.list();
        return ResultUtils.success("查询成功",list);
    }

    @RequestMapping("/image")
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置页面缓存方式 不缓存，不存储
        response.setHeader("Cache-Control", "no-store, no-cache");
        //设置以图片的形式响应
        response.setContentType("image/jpeg");
        //1.获取验证码字符串
        String code = defaultKaptcha.createText();
        log.info("生成的图形验证码是：" + code);
        //2.字符串把它放到session中
        request.getSession().setAttribute(IMAGE_CODE_SESSION_KEY,code);
        //3.生成验证码图片
        BufferedImage image = defaultKaptcha.createImage(code);
        //4.将验证码图片输出给前端
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        if (out != null) {
            out.close();
        }
    }
}
