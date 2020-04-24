package com.lkyi.controller;


import com.lkyi.damain.po.User;
import com.lkyi.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author lkyi
 * @since 2020-04-24
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("添加一个用户")
    @PostMapping("/insertOneUser")
    public String insertOneUser(@RequestBody User user) {
        boolean result = userService.save(user);
        return "添加一个用户，result: " + result;
    }


}
