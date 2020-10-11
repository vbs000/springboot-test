package com.nxy.security.image_code;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常处理
 */
public class ImageCodeException extends AuthenticationException {
    public ImageCodeException(String msg) {
        super(msg);
    }
}
