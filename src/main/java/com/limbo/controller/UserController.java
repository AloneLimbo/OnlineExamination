package com.limbo.controller;

import com.limbo.dto.Result;
import com.limbo.entity.User;
import com.limbo.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;


/**
 *
 * Created by limbo on 17-4-5.
 */
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/api/checkLevel")
    public Result checkLevel(HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        return new Result(0,"Ok",new Date().getTime(),claims.get("status"));
    }

    /**
     * 注册
     * @param user 用户试题类
     * @return Result
     */
    @PostMapping(value = "/api/user")
    public Result userSignUp(@RequestBody User user){
        return userService.signUp(user);
    }

    /**
     * 用户名验证
     * @param username 用户名
     * @return Result
     */
    @GetMapping(value = "/api/userName/exists/{username}")
    public Result userExists(@PathVariable("username") String username){
        return userService.userNameExists(username);
    }

    /**
     * 登录
     * @param loginData User
     * @return Result
     */
    @PostMapping(value = "/user/login")
    public Result login(@RequestBody final User loginData){
        return userService.login(loginData);
    }


    @PostMapping(value = "/api/users")
    public Result batchInsertUser(@RequestParam("file") MultipartFile uploadFiles, HttpServletRequest request) throws IOException {
        Claims claims = (Claims) request.getAttribute("claims");

        return userService.batchInsertUser((FileInputStream) uploadFiles.getInputStream(),claims.getId());

    }

    @GetMapping(value = "/api/users/{offset}")
    public Result queryUsersByFounderId(@PathVariable("offset") int offset, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        return userService.queryUsersByFounderId(claims.getId(),offset);
    }

    @PostMapping(value = "/api/addUser")
    public Result addUser(@RequestBody User user, HttpServletRequest request){
        return userService.addUser(user);
    }

    @DeleteMapping(value = "/api/user/{userId}/{branchId}")
    public Result deleteUserById(@PathVariable("userId") String userId, @PathVariable("branchId") String branchId, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        return userService.deleteUserById(userId,branchId,claims.getId());
    }


    @GetMapping(value = "/api/userCorrect/{branchId}")
    public Result getUserExamCorrect(@PathVariable("branchId") String branchId){
        return userService.queryUserExamCorrect(branchId);
    }

}
