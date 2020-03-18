package com;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @RequestMapping("test")
    public@ResponseBody
    String test(Param param){
        System.out.println("-----------dadsa---------------");
        return param.getName();
    }
}
