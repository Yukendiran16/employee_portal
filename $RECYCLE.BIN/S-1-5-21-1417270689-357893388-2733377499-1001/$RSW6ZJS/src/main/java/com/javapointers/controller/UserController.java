package com.javapointers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Jerry Conde, webmaster@javapointers.com
 * @since 8/9/2016
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping
    public String viewUserProfile() {
        return "user/profile";
    }
}
