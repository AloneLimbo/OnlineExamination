package com.limbo.controller;

import com.limbo.dto.Result;
import com.limbo.service.PageInfoService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 界面信息Controller
 * Created by limbo on 17-4-7.
 */
@RestController
public class PageInfoController {

    private final PageInfoService pageInfoService;

    @Autowired
    public PageInfoController(PageInfoService pageInfoService) {
        this.pageInfoService = pageInfoService;
    }

    /**
     *  获取测侧边栏信息
     * @param request request
     * @return Result
     */
    @GetMapping(value = "/api/sidebar")
    public Result getSidebar(HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        return pageInfoService.getFunctions((Integer) claims.get("status"));
    }


    @GetMapping(value = "/api/userName")
    public Result getUserName(HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        return new Result(0,"Ok",new Date().getTime(),claims.get("user_name"));
    }


}
