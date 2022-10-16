package com.javapointers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Jerry Conde, webmaster@javapointers.com
 * @since 8/9/2016
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping
    public String viewManageUsers() {
        return "/admin/manage-users";
    }
}
