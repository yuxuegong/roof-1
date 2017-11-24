package com.roof.demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by zhenglt on 2017/11/16.
 */
@ApiModel(value = "Adddd", description = "用户对象")
public class Adddd {

    @ApiModelProperty(value = "ddd",example = "ss")
    private String a;
    @ApiModelProperty(value = "sss",example = "ss")
    private Integer b;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }
}
