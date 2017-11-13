package com.roof.demo.action;

import org.roof.spring.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhenglt on 2017/11/13.
 */
@Controller
@RequestMapping("demo")
public class DemoAction {

    @RequestMapping("/base")
    public @ResponseBody
    Result base() {
        return new Result(Result.SUCCESS);
    }
}
