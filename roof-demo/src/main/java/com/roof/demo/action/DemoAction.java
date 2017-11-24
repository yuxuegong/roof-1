package com.roof.demo.action;

import com.google.common.collect.Lists;
import com.roof.demo.Adddd;
import io.swagger.annotations.ApiOperation;
import org.roof.spring.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhenglt on 2017/11/13.
 */
@Controller
@RequestMapping("demo")
public class DemoAction {

    @ApiOperation(value="创建用户-传递简单对象", notes="传递简单对象",response=Adddd.class)
    @RequestMapping("/base")
    public @ResponseBody
    Result base() {
        Adddd d = new Adddd();

        return new Result(Result.SUCCESS,d);
    }

    @ApiOperation(value="创建用户-传递简单对象1", notes="传递简单对象1")
    @RequestMapping("/base1")
    public @ResponseBody
    List<Adddd> base1() {
        Adddd d = new Adddd();
        List<Adddd> ss =  Lists.newArrayList();
        ss.add(d);
        return ss;
    }
    @ApiOperation(value="创建用户-传递简单对象2", notes="传递简单对象2")
    @RequestMapping("/base2")
    public Result<Adddd> base2() {
        Adddd d = new Adddd();
        List<Adddd> ss =  Lists.newArrayList();
        ss.add(d);
        Result<Adddd> result =  new Result(Result.SUCCESS,d);

        return result;
    }
}
