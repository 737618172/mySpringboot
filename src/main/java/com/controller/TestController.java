package com.controller;

import com.entity.Param;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

//    @Autowired
//    ProxyObject proxyObject;

    @Autowired
    private UserService userService;

    @RequestMapping("test")
    public@ResponseBody
    String test(Param param){
//        System.out.println(proxyObject);
        return param.getName();
    }
}
